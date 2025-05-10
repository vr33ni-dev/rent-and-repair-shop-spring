package com.example.shop.controller;

import com.example.shop.model.Bill;
import com.example.shop.repository.BillRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillingController {

    private final BillRepository billRepository;

    public BillingController(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    // List all bills
    @GetMapping("/all")
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    // List bills by user
    @GetMapping("/user/{userId}")
    public List<Bill> getBillsByUser(@PathVariable Long userId) {
        return billRepository.findAll().stream()
                .filter(b -> userId.equals(b.getUserId()))
                .toList();
    }

    // List bills by surfboard
    @GetMapping("/board/{surfboardId}")
    public List<Bill> getBillsByBoard(@PathVariable Long surfboardId) {
        return billRepository.findAll().stream()
                .filter(b -> surfboardId.equals(b.getSurfboardId()))
                .toList();
    }
}
