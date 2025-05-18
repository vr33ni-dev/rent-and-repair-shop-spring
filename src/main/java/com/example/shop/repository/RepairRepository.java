package com.example.shop.repository;

 import com.example.shop.enums.RepairStatus;
import com.example.shop.model.Repair;
import org.springframework.data.jpa.repository.JpaRepository;
 
import java.util.List;
import java.util.Optional;
import java.util.UUID;

 
public interface RepairRepository extends JpaRepository<Repair, UUID> {

    List<Repair> findBySurfboardId(UUID surfboardId);
    List<Repair> findByCustomerId(UUID customerId);
    List<Repair> findBySurfboardIdAndStatusNot(UUID surfboardId, RepairStatus status);
    Optional<Repair> findTopBySurfboardIdAndCustomerIdOrderByCreatedAtDesc(UUID surfboardId, UUID customerId);
    boolean existsBySurfboardIdAndStatusNot(UUID surfboardId, RepairStatus status);


}
