package com.example.shop.repository;

import com.example.shop.model.Customer;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
 

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findById(UUID id);
    Optional<Customer> findByName(String name);
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhone(String phone);

 }
