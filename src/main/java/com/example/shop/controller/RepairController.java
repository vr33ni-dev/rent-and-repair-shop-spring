package com.example.shop.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.dto.RepairRequest;
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
    public List<Repair> getAllRepairs() {
        return repairRepository.findAll();
    }
    @GetMapping("/user/{userId}")
    public List<Repair> getRepairsByUser(@PathVariable Long userId) {
        return repairRepository.findByUserId(userId);
    }

    @PostMapping("/{repairId}/complete")
    public String markRepairAsCompleted(@PathVariable Long repairId) {
        repairService.markRepairAsCompleted(repairId);
        return "Repair completed!";
    }

    @PostMapping("/{repairId}/cancel")
    public String cancelRepair(@PathVariable Long repairId) {
        repairService.cancelRepair(repairId);
        return "Repair canceled!";
    }
 
}
