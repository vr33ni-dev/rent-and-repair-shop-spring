package com.example.shop.repository;

 import com.example.shop.model.Repair;
import org.springframework.data.jpa.repository.JpaRepository;
 
import java.util.List;

 
public interface RepairRepository extends JpaRepository<Repair, Long> {

    List<Repair> findBySurfboardId(Long surfboardId);
    List<Repair> findByCustomerId(Long customerId);
    List<Repair> findBySurfboardIdAndStatusNot(Long surfboardId, String status);

}
