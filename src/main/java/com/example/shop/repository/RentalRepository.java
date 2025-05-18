package com.example.shop.repository;

import com.example.shop.enums.RentalStatus;
import com.example.shop.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RentalRepository extends JpaRepository<Rental, UUID> {
    List<Rental> findByCustomerId(UUID customerId);
    List<Rental> findBySurfboardId(UUID surfboardId);
    List<Rental> findByStatus(RentalStatus status);
    boolean existsBySurfboardIdAndReturnedAtIsNull(UUID surfboardId);

}
