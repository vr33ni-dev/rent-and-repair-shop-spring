package com.example.shop.service;

import com.example.shop.dto.RepairMessage;
import com.example.shop.enums.BillStatus;
import com.example.shop.dto.BillResponseDTO;
import com.example.shop.dto.RentalMessage;
import com.example.shop.model.Bill;
import com.example.shop.model.Customer;
import com.example.shop.model.Rental;
import com.example.shop.model.Repair;
import com.example.shop.repository.BillingRepository;
import com.example.shop.repository.CustomerRepository;
import com.example.shop.repository.RentalRepository;
import com.example.shop.repository.RepairRepository;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillingService {

    private final BillingRepository billingRepository;
    private final CustomerRepository customerRepository;
    private final RentalRepository rentalRepository;
    private final RepairRepository repairRepository;

    public BillingService(BillingRepository billingRepository, CustomerRepository customerRepository,
            RepairRepository repairRepository,
            RentalRepository rentalRepository) {
        this.customerRepository = customerRepository;
        this.repairRepository = repairRepository;
        this.rentalRepository = rentalRepository;
        this.billingRepository = billingRepository;
    }

    public List<BillResponseDTO> getAllBillDTOs() {
        List<Bill> bills = billingRepository.findAll();

        return bills.stream().map(bill -> {
            String customerName = customerRepository.findById(bill.getCustomerId())
                    .map(Customer::getName)
                    .orElse("Unknown Customer");

            LocalDateTime rentalDate = bill.getRentalId() != null
                    ? rentalRepository.findById(bill.getRentalId())
                            .map(Rental::getRentedAt)
                            .orElse(null)
                    : null;

            LocalDateTime repairDate = bill.getRepairId() != null
                    ? repairRepository.findById(bill.getRepairId())
                            .map(Repair::getCreatedAt)
                            .orElse(null)
                    : null;

            return new BillResponseDTO(
                    bill.getId(),
                    bill.getCustomerId(),
                    customerName,
                    bill.getDescription(),
                    bill.getRentalId(),
                    bill.getRepairId(),
                    bill.getRentalFee(),
                    bill.getRepairFee(),
                    bill.getTotalAmount(),
                    bill.getStatus(),
                    bill.getCreatedAt(),
                    rentalDate,
                    repairDate);
        }).collect(Collectors.toList());
    }

    @RabbitListener(queues = "repair.completed.queue")
    public void handleRepairCompleted(RepairMessage msg) {
        Long customerId = msg.getCustomerId();
        Long rentalId = msg.getRentalId(); // ← will be null for user-owned boards
        Long repairId = repairRepository
                .findTopBySurfboardIdAndCustomerIdOrderByCreatedAtDesc(
                        msg.getSurfboardId(), customerId)
                .map(Repair::getId)
                .orElse(null);

        Bill bill;
        if (rentalId != null) {
            // 1) Rental-case: find or create an OPEN bill *for that rental*.
            bill = billingRepository
                    .findTopByCustomerIdAndRentalIdAndStatusOrderByCreatedAtDesc(
                            customerId, rentalId, BillStatus.OPEN)
                    .orElseGet(() -> {
                        Bill b = new Bill();
                        b.setCustomerId(customerId);
                        b.setRentalId(rentalId);
                        b.setCreatedAt(LocalDateTime.now());
                        b.setStatus(BillStatus.OPEN);
                        b.setRentalFee(0.0);
                        b.setRepairFee(0.0);
                        return billingRepository.save(b);
                    });
        } else {
            // 2) User-owned case: find or create an OPEN bill *with no rental*.
            bill = billingRepository
                    .findTopByCustomerIdAndRentalIdIsNullAndStatusOrderByCreatedAtDesc(
                            customerId, BillStatus.OPEN)
                    .orElseGet(() -> {
                        Bill newBill = new Bill();
                        newBill.setCustomerId(customerId);
                        newBill.setCreatedAt(LocalDateTime.now());
                        newBill.setStatus(BillStatus.OPEN);
                        newBill.setRentalFee(0.0);
                        newBill.setRepairFee(0.0);
                        newBill.setTotalAmount(0.0);
                        return billingRepository.save(newBill);
                    });
        }

        // 3) Add the repair fee:
        bill.setRepairFee(bill.getRepairFee() + 49.99);
        bill.setRepairId(repairId);

        // 4) Set a clear description:
        if (rentalId != null) {
            bill.setDescription(
                    String.format("Rental fee for #%d plus repair fee for board #%d",
                            rentalId, msg.getSurfboardId()));
        } else {
            bill.setDescription(
                    "Repair fee for personal board ID " + msg.getSurfboardId());
        }

        // 5) Update totals and save
        double total = (bill.getRentalFee() != null ? bill.getRentalFee() : 0)
                + (bill.getRepairFee() != null ? bill.getRepairFee() : 0);
        bill.setTotalAmount(total);
        bill.setUpdatedAt(LocalDateTime.now());
        bill.setStatus(BillStatus.COMPLETED);
        billingRepository.save(bill);

        System.out.println("💸 Repair fee added to bill for customer ID: " + customerId
                + (rentalId != null ? " (rental #" + rentalId + ")" : ""));
    }

    @RabbitListener(queues = "rental.completed.queue")
    public void handleRentalCompleted(RentalMessage msg) {
        // 1) lookup by both customerId & rentalId
        Bill bill = billingRepository
                .findTopByCustomerIdAndRentalIdAndStatusOrderByCreatedAtDesc(
                        msg.getCustomerId(),
                        msg.getRentalId(),
                        BillStatus.OPEN)
                .orElseGet(() -> {
                    // 2) if none, make a fresh Bill for *this* rental
                    Bill newBill = new Bill();
                    newBill.setCustomerId(msg.getCustomerId());
                    newBill.setRentalId(msg.getRentalId());
                    newBill.setCreatedAt(LocalDateTime.now());
                    newBill.setStatus(BillStatus.OPEN);
                    newBill.setRentalFee(0.0);
                    newBill.setRepairFee(0.0);
                    newBill.setTotalAmount(0.0);
                    return billingRepository.save(newBill);
                });

        // 3) now safely accumulate your rental fee
        bill.setRentalFee(bill.getRentalFee() + 19.99);
        double rental = bill.getRentalFee() != null ? bill.getRentalFee() : 0.0;
        double repair = bill.getRepairFee() != null ? bill.getRepairFee() : 0.0;
        bill.setTotalAmount(rental + repair); // ← recompute here
        bill.setDescription("Rental fee for rental ID: " + msg.getRentalId());
        bill.setUpdatedAt(LocalDateTime.now());
        bill.setStatus(BillStatus.COMPLETED);
        billingRepository.save(bill);

        System.out.println("💸 Rental fee added to bill for customer ID: " + msg.getCustomerId());
    }


    public void markBillAsPaid(Long id) {
        Bill bill = billingRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Bill not found: " + id));

        bill.setStatus(BillStatus.PAID);
        bill.setUpdatedAt(LocalDateTime.now());
        billingRepository.save(bill);
    }

}
