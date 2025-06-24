package com.example.shop.service;

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

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

 public class RentalService {

    private final RentalRepository rentalRepository;
    private final RepairRepository repairRepository;
    private final SurfboardRepository surfboardRepository;
    private final CustomerRepository customerRepository;
    private final BillingRepository billingRepository;
    private final SettingsService settingsService;

    public RentalService(RentalRepository rentalRepository, SurfboardRepository surfboardRepository,
            CustomerRepository customerRepository, BillingRepository billingRepository,
            RepairRepository repairRepository, SettingsService settingsService) {
        this.rentalRepository = rentalRepository;
        this.surfboardRepository = surfboardRepository;
        this.customerRepository = customerRepository;
        this.billingRepository = billingRepository;
        this.repairRepository = repairRepository;
        this.settingsService = settingsService;
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
        rental.setRentalFee(
                request.getRentalFee() != null
                        ? request.getRentalFee()
                        : settingsService.getDefaultRentalFee());
        rental.setRentedAt(LocalDateTime.now());
        rental.setStatus(RentalStatus.CREATED);
        rentalRepository.save(rental);

        System.out.println("✅ Rental created and board marked unavailable: " + rental.getId());
    }

    @Transactional
    public void returnBoard(UUID rentalId,
            boolean isDamaged,
            String damageDescription,
            Double repairPrice,
            Double finalFeeOverride) {
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

        // 3) Figure out the total rental‐only fee
        LocalDate start = rental.getRentedAt().toLocalDate();
        LocalDate end = LocalDateTime.now().toLocalDate();
        long days = ChronoUnit.DAYS.between(start, end) + 1; // inclusive
        double dailyRate = Optional.ofNullable(rental.getRentalFee())
                .orElse(15.0); // your default
        double rentalTotal = finalFeeOverride != null
                ? finalFeeOverride
                : days * dailyRate;

        // 4) Create a repair, if necessary
        Repair createdRepair = null;
        if (isDamaged) {
            Repair rep = new Repair();
            rep.setSurfboardId(board.getId());
            rep.setCustomerId(rental.getCustomerId());
            rep.setRentalId(rentalId);
            rep.setIssue(damageDescription);
            rep.setRepairFee(repairPrice != null ? repairPrice : 15.0);
            rep.setStatus(RepairStatus.CREATED);
            rep.setCreatedAt(LocalDateTime.now());
            createdRepair = repairRepository.save(rep);
        }

        // 5) now build the Bill, linking to either the rental or the repair:
        Bill bill = new Bill();
        bill.setCustomerId(rental.getCustomerId());
        if (createdRepair != null) {
            // link this bill to the repair
            bill.setRepairId(createdRepair.getId());
            bill.setRentalId(rentalId);
        } else {
            // no damage: just a rental bill
            bill.setRentalId(rentalId);
        }
        bill.setRepairFee(isDamaged ? createdRepair.getRepairFee() : 0.0);
        bill.setRentalFee(rentalTotal);
        bill.setTotalAmount(isDamaged
                ? (repairPrice != null ? repairPrice : 15.0)
                : rentalTotal);
        bill.setCreatedAt(LocalDateTime.now());
        bill.setStatus(BillStatus.COMPLETED);
        bill.setDescription(isDamaged
                ? String.format("Repair fee for board %s (rental %s)", board.getId(), rentalId)
                : String.format("Rental fee for %d days (rental %s)", days, rentalId));
        billingRepository.save(bill);

        System.out.println("🔄 returnBoard complete: rental=" + rentalId +
                ", damaged=" + isDamaged +
                (createdRepair != null ? ", repair=" + createdRepair.getId() : ""));
    }
}
