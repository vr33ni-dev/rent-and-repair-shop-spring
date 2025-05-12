package com.example.shop.service;

import com.example.shop.dto.RentalMessage;
import com.example.shop.dto.RepairMessage;
import com.example.shop.enums.RentalStatus;
import com.example.shop.dto.RentalRequest;
import com.example.shop.dto.RentalResponseDTO;
import com.example.shop.model.Customer;
import com.example.shop.model.Rental;
import com.example.shop.model.Surfboard;
import com.example.shop.repository.CustomerRepository;
import com.example.shop.repository.RentalRepository;
import com.example.shop.repository.SurfboardRepository;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final SurfboardRepository surfboardRepository;
    private final CustomerRepository customerRepository;

    private final RabbitTemplate rabbitTemplate;

    public RentalService(RentalRepository rentalRepository, SurfboardRepository surfboardRepository,
            CustomerRepository customerRepository,
            RabbitTemplate rabbitTemplate) {
        this.rentalRepository = rentalRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.surfboardRepository = surfboardRepository;
        this.customerRepository = customerRepository;
    }

    public List<RentalResponseDTO> getAllRentalDTOs() {
        return rentalRepository.findAll().stream().map(rental -> {
            Surfboard board = surfboardRepository.findById(rental.getSurfboardId())
                    .orElse(null);

            String boardName = board != null ? board.getName() : "Unknown";

            String customerName = "Shop";
            if (rental.getCustomerId() != null) {
                customerName = customerRepository.findById(rental.getCustomerId())
                        .map(Customer::getName)
                        .orElse("Unknown Customer");
            }

            return new RentalResponseDTO(
                    rental.getId(),
                    rental.getSurfboardId(),
                    rental.getCustomerId(),
                    boardName,
                    customerName,
                    rental.getRentedAt(),
                    rental.getReturnedAt(),
                    rental.getStatus());
        }).toList();
    }

    public void rentBoard(RentalRequest request) {
        String name = request.getCustomerName();
        String contact = request.getCustomerContact();

        // Determine if it's an email or phone
        boolean isEmail = contact.contains("@");

        // Look up existing customer
        Optional<Customer> customerOpt = isEmail
                ? customerRepository.findByEmail(contact)
                : customerRepository.findByPhone(contact);

        Customer customer = customerOpt.orElseGet(() -> {
            Customer newCustomer = new Customer();
            newCustomer.setName(name);
            if (isEmail) {
                newCustomer.setEmail(contact);
            } else {
                newCustomer.setPhone(contact);
            }
            return customerRepository.save(newCustomer);
        });

        // Validate board
        Surfboard board = surfboardRepository.findById(request.getSurfboardId())
                .orElseThrow(() -> new RuntimeException("Surfboard not found with ID: " + request.getSurfboardId()));

        if (!board.isShopOwned()) {
            throw new IllegalStateException("Cannot rent a user-owned board.");
        }

        if (!board.isAvailableForRental()) {
            throw new IllegalStateException("Surfboard not available for rental.");
        }

        // Mark board as unavailable
        board.setAvailable(false);
        surfboardRepository.save(board);

        // Create rental
        Rental rental = new Rental();
        rental.setCustomerId(customer.getId());
        rental.setSurfboardId(board.getId());
        rental.setRentedAt(LocalDateTime.now());
        rental.setStatus(RentalStatus.CREATED);
        Rental savedRental = rentalRepository.save(rental);

        // Emit rental.created message
        rabbitTemplate.convertAndSend("surfboard.exchange", "rental.created", new RentalMessage(
                savedRental.getId(), board.getId(), customer.getId(), false));

        System.out.println("✅ Rental created and board marked unavailable: " + board.getId());
    }

    public void returnBoard(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental not found with ID: " + rentalId));

        Surfboard board = surfboardRepository.findById(rental.getSurfboardId())
                .orElseThrow(
                        () -> new IllegalStateException("Surfboard not found with ID: " + rental.getSurfboardId()));

        // Randomly simulate damage
        boolean isDamaged = new Random().nextBoolean();

        rental.setReturnedAt(LocalDateTime.now());
        rental.setStatus(RentalStatus.RETURNED);
        rentalRepository.save(rental);

        if (isDamaged && !board.isDamaged()) {
            board.setDamaged(true);
            board.setAvailable(false);
            surfboardRepository.save(board);

            // Trigger repair
            RepairMessage repairMsg = new RepairMessage(board.getId(), "Ding on the tail", rental.getCustomerId(), rental.getId());
            rabbitTemplate.convertAndSend("surfboard.exchange", "repair.created", repairMsg);

            System.out.println("🛠️ Repair created for damaged board ID: " + board.getId());
        } else if (!isDamaged) {
            // No damage → finalize return
            board.setAvailable(true);
            board.setDamaged(false);
            surfboardRepository.save(board);

            // Emit billing event (no repair needed)
            RentalMessage rentalMessage = new RentalMessage(rental.getId(), rental.getSurfboardId(),
                    rental.getCustomerId(), false);
            rabbitTemplate.convertAndSend("surfboard.exchange", "rental.completed", rentalMessage);
            System.out.println("💬 rental.completed sent (no damage) for rental ID: " + rental.getId());
        } else {
            System.out.println("⚠️ Damage already tracked. Awaiting repair completion.");
        }

        System.out.println("🔄 Board returned (pending): rental ID " + rentalId);
    }

}
