package com.example.shop.service;

 import com.example.shop.dto.RentalMessage;
import com.example.shop.dto.RepairMessage;
import com.example.shop.enums.BillStatus;
import com.example.shop.enums.RentalStatus;
import com.example.shop.enums.RepairStatus;
import com.example.shop.dto.RentalRequest;
import com.example.shop.dto.RentalResponseDTO;
import com.example.shop.model.Bill;
import com.example.shop.model.Customer;
import com.example.shop.model.Rental;
import com.example.shop.model.Repair;
import com.example.shop.model.Surfboard;
import com.example.shop.repository.BillingRepository;
import com.example.shop.repository.CustomerRepository;
import com.example.shop.repository.RentalRepository;
import com.example.shop.repository.RepairRepository;
import com.example.shop.repository.SurfboardRepository;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final RepairRepository repairRepository;
    private final SurfboardRepository surfboardRepository;
    private final CustomerRepository customerRepository;
    private final BillingRepository billingRepository;

    public RentalService(RentalRepository rentalRepository, SurfboardRepository surfboardRepository,
            CustomerRepository customerRepository, BillingRepository billingRepository,
           RepairRepository repairRepository) {
        this.rentalRepository = rentalRepository;
        this.surfboardRepository = surfboardRepository;
        this.customerRepository = customerRepository;
        this.billingRepository = billingRepository;
        this.repairRepository = repairRepository;
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
                    rental.getRentalFee(),
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
        rental.setRentalFee(request.getRentalFee() != null ? request.getRentalFee() : 15.0);
        rental.setRentedAt(LocalDateTime.now());
        rental.setStatus(RentalStatus.CREATED);
         rentalRepository.save(rental);

      
        System.out.println("✅ Rental created and board marked unavailable: " + rental.getId());
    }
 
    @Transactional
    public void returnBoard(UUID rentalId,
                            boolean isDamaged,
                            String damageDescription,
                            Double repairPrice) {
        // 1) Load & update Rental
        Rental rental = rentalRepository.findById(rentalId)
            .orElseThrow(() -> new RuntimeException("Rental not found: " + rentalId));
        rental.setReturnedAt(LocalDateTime.now());
        rental.setStatus(RentalStatus.RETURNED);
        rentalRepository.save(rental);

        // 2) Load & update Surfboard
        Surfboard board = surfboardRepository.findById(rental.getSurfboardId())
            .orElseThrow(() -> new IllegalStateException("Board not found: " + rental.getSurfboardId()));
        board.setDamaged(isDamaged);
        board.setAvailable(!isDamaged);
        surfboardRepository.save(board);

        // 3) Always create a Bill
        double rentalFee = rental.getRentalFee() != null ? rental.getRentalFee() : 15.0;
        double feeToBill = isDamaged
            ? repairPrice != null ? repairPrice : 0.0
            : rentalFee;

        Bill bill = new Bill();
        bill.setCustomerId(rental.getCustomerId());
        bill.setRentalId(isDamaged ? null : rentalId);
        bill.setRepairId(isDamaged ? null : null); // set below if needed
        bill.setRentalFee(isDamaged ? 0.0 : rentalFee);
        bill.setRepairFee(isDamaged ? feeToBill : 0.0);
        bill.setTotalAmount(feeToBill);
        bill.setCreatedAt(LocalDateTime.now());
        bill.setStatus(BillStatus.COMPLETED);
        bill.setDescription(isDamaged
            ? String.format("Repair fee for board %s, rental %s", board.getId(), rentalId)
            : String.format("Rental fee for rental %s", rentalId));
        billingRepository.save(bill);

        // 4) If damaged, also create a Repair record
        if (isDamaged) {
            Repair repair = new Repair();
            repair.setSurfboardId(board.getId());
            repair.setCustomerId(rental.getCustomerId());
            repair.setRentalId(rentalId);
            repair.setIssue(damageDescription);
            repair.setRepairFee(feeToBill);
            repair.setStatus(RepairStatus.CREATED);
            repair.setCreatedAt(LocalDateTime.now());
            repairRepository.save(repair);
        }

        System.out.println("🔄 returnBoard complete: rental=" + rentalId +
                           ", damaged=" + isDamaged);
    }
}
