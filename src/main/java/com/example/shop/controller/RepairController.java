package com.example.shop.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.dto.RepairRequest;
import com.example.shop.dto.RepairResponseDTO;
import com.example.shop.model.Repair;
import com.example.shop.repository.RepairRepository;
import com.example.shop.service.RepairService;

@RestController
@RequestMapping("/api/repairs")
public class RepairController {

    private final RepairService repairService;
    private final RepairRepository repairRepository;
    
    public RepairController(RepairService repairService, RepairRepository repairRepository) {
        this.repairService = repairService;
        this.repairRepository = repairRepository;
    }

    @PostMapping
    public String createRepair(@RequestBody RepairRequest request) {
        repairService.createRepair(request);
        return "Repair created!";
    }

    @GetMapping("/all")
    public List<RepairResponseDTO> getAllRepairs() {
        return repairService.getAllRepairs();
    }

    @GetMapping("/customer/{customerId}")
    public List<Repair> getRepairsByUser(@PathVariable UUID customerId) {
        return repairRepository.findByCustomerId(customerId);
    }

    @PostMapping("/{repairId}/complete")
     public ResponseEntity<Void> markRepairAsCompleted(@PathVariable UUID id) {
        repairService.markRepairAsCompleted(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{repairId}/cancel")
    public String cancelRepair(@PathVariable UUID repairId) {
        repairService.cancelRepair(repairId);
        return "Repair canceled!";
    }
 
}
