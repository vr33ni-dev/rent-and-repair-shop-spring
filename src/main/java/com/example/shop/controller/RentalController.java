package com.example.shop.controller;

import com.example.shop.dto.RentalRequest;
import com.example.shop.dto.RentalResponseDTO;
import com.example.shop.dto.ReturnRequest;
import com.example.shop.enums.RentalStatus;
import com.example.shop.model.Rental;
import com.example.shop.repository.RentalRepository;
import com.example.shop.service.RentalService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;
    private final RentalRepository rentalRepository;

    public RentalController(RentalService rentalService, RentalRepository rentalRepository) {
        this.rentalService = rentalService;
        this.rentalRepository = rentalRepository;
    }

    // Create new rental
    @PostMapping
    public String rent(@RequestBody RentalRequest request) {
        rentalService.rentBoard(request);
        return "Rental requested!";
    }

    // Return rented board
   @PostMapping("/{id}/return")
    public ResponseEntity<Void> returnBoard(
        @PathVariable UUID id,
        @RequestBody ReturnRequest req
    ) {
        rentalService.returnBoard(
            id,
            req.isDamaged(),
            req.getDamageDescription(),
            req.getRepairPrice()
        );
        return ResponseEntity.ok().build();
    }

    // List all rentals
    @GetMapping("/all")
    public List<RentalResponseDTO> getAllRentals() {
        return rentalService.getAllRentalDTOs();
    }

    // Filter by customerId
    @GetMapping("/customer/{customerId}")
    public List<Rental> getRentalsByUser(@PathVariable UUID customerId) {
        return rentalRepository.findByCustomerId(customerId);
    }

    // Filter by status (e.g., REQUESTED, RENTED, RETURNED)
    @GetMapping("/status/{status}")
    public List<Rental> getRentalsByStatus(@PathVariable RentalStatus status) {
        return rentalRepository.findByStatus(status);
    }
}
