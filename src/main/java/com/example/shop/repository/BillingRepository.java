package com.example.shop.repository;

import com.example.shop.enums.BillStatus;
import com.example.shop.model.Bill;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingRepository extends JpaRepository<Bill, UUID> {

  // For rental-case
  Optional<Bill> findTopByCustomerIdAndRentalIdAndStatusOrderByCreatedAtDesc(
    UUID customerId,
      UUID rentalId,
      BillStatus status);

  // For rental-case
  Optional<Bill> findTopByCustomerIdAndRentalIdOrderByCreatedAtDesc(
    UUID customerId,
    UUID rentalId);

      boolean existsByCustomerIdAndRentalId(UUID customerId, UUID rentalId);

  // For user-owned case
  Optional<Bill> findTopByCustomerIdAndRentalIdIsNullOrderByCreatedAtDesc(
    UUID customerId);


  Optional<Bill> findTopByCustomerIdAndRentalIdIsNullAndStatusOrderByCreatedAtDesc(
    UUID customerId, BillStatus status);

  boolean existsByCustomerIdAndRepairId(UUID customerId, UUID repairId);
}
