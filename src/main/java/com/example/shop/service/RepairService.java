package com.example.shop.service;

import com.example.shop.dto.RepairRequest;
import com.example.shop.dto.RepairResponseDTO;
import com.example.shop.enums.BillStatus;
import com.example.shop.enums.RepairStatus;
import com.example.shop.model.Bill;
import com.example.shop.model.Customer;
import com.example.shop.model.Repair;
import com.example.shop.model.Surfboard;
import com.example.shop.repository.BillingRepository;
import com.example.shop.repository.CustomerRepository;
import com.example.shop.repository.RentalRepository;
import com.example.shop.repository.RepairRepository;
import com.example.shop.repository.SurfboardRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RepairService {

    private final RepairRepository repairRepository;
    private final BillingRepository billingRepository;
    private final SurfboardRepository surfboardRepository;
    private final CustomerRepository customerRepository;

    public RepairService(RepairRepository repairRepository, SurfboardRepository surfboardRepository,
            CustomerRepository customerRepository,
            BillingRepository billingRepository, RentalRepository rentalRepository) {
        this.repairRepository = repairRepository;
        this.surfboardRepository = surfboardRepository;
        this.customerRepository = customerRepository;
        this.billingRepository = billingRepository;
    }

    public List<RepairResponseDTO> getAllRepairs() {
        List<Repair> repairs = repairRepository.findAll();

        return repairs.stream().map(repair -> {
            Surfboard board = surfboardRepository.findById(repair.getSurfboardId())
                    .orElseThrow(
                            () -> new IllegalStateException("Surfboard not found for repair ID: " + repair.getId()));

            String boardName = null;
            if (repair.getSurfboardId() != null) {
                boardName = surfboardRepository.findById(repair.getSurfboardId())
                        .map(Surfboard::getName)
                        .orElse(null);
            }
            String customerName = "Shop";

            if (repair.getCustomerId() != null) {
                customerName = customerRepository.findById(repair.getCustomerId())
                        .map(Customer::getName)
                        .orElse("Unknown Customer");
            }

            return new RepairResponseDTO(
                    repair.getId(),
                    repair.getSurfboardId(),
                    repair.getCustomerId(),
                    repair.getRentalId(),
                    boardName,              
                    repair.getIssue(),
                    repair.getStatus(),
                    repair.getCreatedAt(),
                    customerName,
                    repair.getRepairFee()
                    );
        }).collect(Collectors.toList());
    }

    public void createRepair(RepairRequest request) {
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

        // 2) Determine the boardId: either existing shop board, or new customer board
        UUID boardId;
        if (request.getSurfboardId() != null) {
            // shop-owned repair
            boardId = request.getSurfboardId();
        } else {
            // customer-owned: create a new Surfboard row
            Surfboard board = new Surfboard();
            board.setName(request.getSurfboardName());
            board.setShopOwned(false);
            board.setAvailable(false);
            board.setDamaged(true);
            board = surfboardRepository.save(board);
            boardId = board.getId();
        }

        boolean repairExists = repairRepository
                .findBySurfboardIdAndStatusNot(boardId, RepairStatus.COMPLETED)
                .stream()
                .anyMatch(r -> r.getStatus() != RepairStatus.CANCELED);

        if (repairExists) {
            System.out.println("⚠️ Repair already exists for surfboard ID: " + boardId + ", skipping.");
            return;
        }

        Repair repair = new Repair();
        repair.setSurfboardId(boardId);
        repair.setCustomerId(customer.getId());
        repair.setIssue(request.getIssue());
        repair.setStatus(RepairStatus.CREATED);
        repair.setRepairFee(request.getRepairFee());
        repair.setCreatedAt(LocalDateTime.now());  
        repairRepository.save(repair);
        System.out.println("Manual repair created with ID: " + repair.getId());
    }

    @Transactional
    public void markRepairAsCompleted(UUID repairId) {
        Repair repair = repairRepository.findById(repairId)
                .orElseThrow(() -> new RuntimeException("Repair not found: " + repairId));
        repair.setCompletedAt(LocalDateTime.now());
        repair.setStatus(RepairStatus.COMPLETED);
        repairRepository.save(repair);

        // make board available again
        Surfboard board = surfboardRepository.findById(repair.getSurfboardId())
                .orElseThrow(() -> new RuntimeException("Board not found: " + repair.getSurfboardId()));
        board.setDamaged(false);
        board.setAvailable(true);
        surfboardRepository.save(board);

        // create a “repair completed” bill if not already created
        boolean exists = billingRepository
                .existsByCustomerIdAndRepairId(repair.getCustomerId(), repairId);
        if (!exists) {
            Bill bill = new Bill();
            bill.setCustomerId(repair.getCustomerId());
            bill.setRepairId(repairId);
            bill.setRentalId(repair.getRentalId());
            bill.setRepairFee(repair.getRepairFee());
            bill.setRentalFee(0.0);
            bill.setTotalAmount(repair.getRepairFee());
            bill.setCreatedAt(LocalDateTime.now());
            bill.setStatus(BillStatus.COMPLETED);
            bill.setDescription(String.format("Repair fee for board %s", board.getId()));
            billingRepository.save(bill);
        }

        System.out.println("🛠️ completeRepair done: " + repairId);
    }

    public void cancelRepair(UUID repairId) {
        Repair repair = repairRepository.findById(repairId)
                .orElseThrow(() -> new IllegalArgumentException("Repair not found with ID: " + repairId));

        if (repair.getStatus() == RepairStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel a completed repair.");
        }

        repair.setStatus(RepairStatus.CANCELED);
        repairRepository.save(repair);

        System.out.println("Repair canceled: ID " + repairId);
    }

}
