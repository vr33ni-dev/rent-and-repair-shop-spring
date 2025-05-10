package com.example.shop.repository;

import com.example.shop.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingRepository extends JpaRepository<Bill, Long> {
}
