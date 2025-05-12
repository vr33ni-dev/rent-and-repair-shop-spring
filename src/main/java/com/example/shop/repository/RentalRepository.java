package com.example.shop.repository;

import com.example.shop.enums.RentalStatus;
import com.example.shop.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByCustomerId(Long customerId);
    List<Rental> findBySurfboardId(Long surfboardId);
    List<Rental> findByStatus(RentalStatus status);
}
