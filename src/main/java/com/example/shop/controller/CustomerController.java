package com.example.shop.controller;

import com.example.shop.model.Customer;
import com.example.shop.repository.CustomerRepository;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Profile("!keepalive")
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerRepository customerRepo;

    public CustomerController(CustomerRepository customerRepo) {
        this.customerRepo = customerRepo;
    }

    // List all customers
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    // Create a new customer
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerRepo.save(customer);
    }

    // Get customer by ID (optional)
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable UUID id) {
        return customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));
    }
}
