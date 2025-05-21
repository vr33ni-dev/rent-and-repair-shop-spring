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

    private final InventoryService inventoryService;

    public SurfboardController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/all")
    public List<SurfboardResponseDTO> all() {
        return inventoryService.getAllBoards();
    }

    @GetMapping("/available")
    public List<SurfboardResponseDTO> available() {
        return inventoryService.getAvailableBoards();
    }

    @PostMapping
    public ResponseEntity<SurfboardResponseDTO> addBoard(
            @Validated @RequestBody SurfboardRequest request) {
        SurfboardResponseDTO dto = inventoryService.createSurfboard(request);
        return ResponseEntity.ok(dto);
    }

}
