package com.example.shop.repository;

import com.example.shop.enums.BillStatus;
import com.example.shop.model.Bill;
 
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingRepository extends JpaRepository<Bill, Long> {

 
// For rental-case
Optional<Bill> 
  findTopByCustomerIdAndRentalIdAndStatusOrderByCreatedAtDesc(
      Long customerId,
      Long rentalId,
      BillStatus status
  );


// For user-owned case
Optional<Bill>
  findTopByCustomerIdAndRentalIdIsNullAndStatusOrderByCreatedAtDesc(
      Long customerId, BillStatus status);
}
