package com.example.shop.repository;

 import com.example.shop.enums.RepairStatus;
import com.example.shop.model.Repair;
import org.springframework.data.jpa.repository.JpaRepository;
 
import java.util.List;
import java.util.Optional;

 
public interface RepairRepository extends JpaRepository<Repair, Long> {

    List<Repair> findBySurfboardId(Long surfboardId);
    List<Repair> findByCustomerId(Long customerId);
    List<Repair> findBySurfboardIdAndStatusNot(Long surfboardId, RepairStatus status);
    Optional<Repair> findTopBySurfboardIdAndCustomerIdOrderByCreatedAtDesc(Long surfboardId, Long customerId);


}
