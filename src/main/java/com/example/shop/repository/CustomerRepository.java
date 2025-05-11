package com.example.shop.repository;

import com.example.shop.model.Customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
 

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findById(Long id);
    Optional<Customer> findByName(String name);
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhone(String phone);

 }
