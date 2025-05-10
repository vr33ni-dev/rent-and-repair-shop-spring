package com.example.shop.service;

import com.example.shop.dto.RepairMessage;
import com.example.shop.dto.RepairResponseDTO;
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

    public BillingService(BillingRepository billingRepository, CustomerRepository customerRepository, RepairRepository repairRepository, 
            RentalRepository rentalRepository) {
        this.customerRepository = customerRepository;
        this.repairRepository = repairRepository;
        this.rentalRepository = rentalRepository;
        this.billingRepository = billingRepository;
    }

    public List<BillResponseDTO> getAllBillDTOs() {
         List<Bill> bills = billingRepository.findAll();

    return bills.stream().map(bill -> {
        // Default values
        String customerName = "Shop";
        LocalDateTime originDate = null;

        // Lookup customer name
        if (bill.getCustomerId() != null) {
            customerName = customerRepository.findById(bill.getCustomerId())
                    .map(Customer::getName)
                    .orElse("Unknown Customer");
        }

        // Determine origin date based on description
        if (bill.getDescription().toLowerCase().contains("rental")) {
            originDate = rentalRepository.findBySurfboardId(bill.getSurfboardId()).stream()
                    .findFirst()
                    .map(Rental::getRentedAt)
                    .orElse(null);
        } else if (bill.getDescription().toLowerCase().contains("repair")) {
            originDate = repairRepository.findBySurfboardId(bill.getSurfboardId()).stream()
                    .findFirst()
                    .map(Repair::getCreatedAt)
                    .orElse(null);
        }

        return new BillResponseDTO(
                bill.getId(),
                bill.getSurfboardId(),
                bill.getCustomerId(),
                customerName,
                bill.getAmount(),
                bill.getDescription(),
                bill.getCreatedAt(),
                originDate
        );
    }).collect(Collectors.toList());
}


    @RabbitListener(queues = "repair.completed.queue")
    public void handleRepairCompleted(RepairMessage msg) {
        Bill bill = new Bill();
        bill.setSurfboardId(msg.getSurfboardId());
        bill.setCustomerId(msg.getCustomerId());  
        bill.setAmount(49.99);
        bill.setDescription("Repair: " + msg.getIssue());
        bill.setCreatedAt(LocalDateTime.now());

        billingRepository.save(bill);
        System.out.println("💸 Bill generated for repair on board ID: " + msg.getSurfboardId());
    }

    @RabbitListener(queues = "rental.completed.queue")
    public void handleRentalCompleted(RentalMessage msg) {
        Bill bill = new Bill();
        bill.setSurfboardId(msg.getSurfboardId());
        bill.setCustomerId(msg.getCustomerId());
        bill.setAmount(19.99); // flat-rate or calculate based on duration if desired
        bill.setDescription("Rental fee");
        bill.setCreatedAt(LocalDateTime.now());

        billingRepository.save(bill);
        System.out.println("💸 Bill generated for rental by user ID: " + msg.getCustomerId());
    }
}
