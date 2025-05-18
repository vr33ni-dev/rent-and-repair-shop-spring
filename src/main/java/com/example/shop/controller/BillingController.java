package com.example.shop.controller;

import com.example.shop.dto.BillResponseDTO;
import com.example.shop.model.Bill;
import com.example.shop.repository.BillingRepository;
import com.example.shop.service.BillingService;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bills")
public class BillingController {

    private final BillingService billingService;
    private final BillingRepository billingRepository;

    public BillingController(BillingRepository billingRepository, BillingService billingService) {
        this.billingRepository = billingRepository;
        this.billingService = billingService;
    }

    // List all bills
    @GetMapping("/all")
    public List<BillResponseDTO> getAllBills() {
        return billingService.getAllBillDTOs();
    }

    // List bills by user
    @GetMapping("/customer/{customerId}")
    public List<Bill> getBillsByUser(@PathVariable UUID customerId) {
        return billingRepository.findAll().stream()
                .filter(b -> customerId.equals(b.getCustomerId()))
                .toList();
    }

    @PostMapping("/{id}/pay")
    public void payBill(@PathVariable UUID id) {
        billingService.markBillAsPaid(id);
    }

}
