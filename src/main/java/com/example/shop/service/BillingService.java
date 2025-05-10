package com.example.shop.service;

import com.example.shop.dto.RepairMessage;
import com.example.shop.dto.RentalMessage;
import com.example.shop.model.Bill;
import com.example.shop.repository.BillRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BillingService {

    private final BillRepository billRepository;

    public BillingService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @RabbitListener(queues = "repair.completed.queue")
    public void handleRepairCompleted(RepairMessage msg) {
        Bill bill = new Bill();
        bill.setSurfboardId(msg.getSurfboardId());
        bill.setUserId(msg.getCustomerId());  
        bill.setAmount(49.99);
        bill.setDescription("Repair: " + msg.getIssue());
        bill.setCreatedAt(LocalDateTime.now());

        billRepository.save(bill);
        System.out.println("💸 Bill generated for repair on board ID: " + msg.getSurfboardId());
    }

    @RabbitListener(queues = "rental.completed.queue")
    public void handleRentalCompleted(RentalMessage msg) {
        Bill bill = new Bill();
        bill.setSurfboardId(msg.getSurfboardId());
        bill.setUserId(msg.getCustomerId());
        bill.setAmount(19.99); // flat-rate or calculate based on duration if desired
        bill.setDescription("Rental fee");
        bill.setCreatedAt(LocalDateTime.now());

        billRepository.save(bill);
        System.out.println("💸 Bill generated for rental by user ID: " + msg.getCustomerId());
    }
}
