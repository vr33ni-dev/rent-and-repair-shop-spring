package com.example.shop.controller;

import com.example.shop.dto.SurfboardRequest;
import com.example.shop.dto.SurfboardResponseDTO;
import com.example.shop.model.Surfboard;
import com.example.shop.repository.SurfboardRepository;
import com.example.shop.service.InventoryService;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/surfboards")
public class SurfboardController {

    private final SurfboardRepository surfboardRepository;
    private final InventoryService inventoryService;


    public SurfboardController(InventoryService inventoryService, SurfboardRepository surfboardRepository) {
        this.inventoryService = inventoryService;
        this.surfboardRepository = surfboardRepository;
    }

    @GetMapping("/all")
    public List<Surfboard> getAllSurfboards() {
        return surfboardRepository.findAll();
    }

    @GetMapping("/available")
    public List<Surfboard> getAvailableBoards() {
        return surfboardRepository.findAll()
                .stream()
                .filter(Surfboard::isAvailableForRental)
                .toList();
    }

    @PostMapping
    public ResponseEntity<SurfboardResponseDTO> addBoard(
            @Validated @RequestBody SurfboardRequest request) {
        SurfboardResponseDTO dto = inventoryService.createSurfboard(request);
        return ResponseEntity.ok(dto);
    }

}
