package com.example.shop.controller;

import com.example.shop.dto.RentalRequest;
import com.example.shop.dto.RentalResponseDTO;
import com.example.shop.model.Rental;
import com.example.shop.repository.RentalRepository;
import com.example.shop.service.RentalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping("/{rentalId}/return")
    public String returnBoard(@PathVariable Long rentalId) {
        rentalService.returnBoard(rentalId);
        return "Board returned!";
    }

    // List all rentals
    @GetMapping("/all")
    public List<RentalResponseDTO> getAllRentals() {
        return rentalService.getAllRentalDTOs();
    }

    // Filter by customerId
    @GetMapping("/customer/{customerId}")
    public List<Rental> getRentalsByUser(@PathVariable Long customerId) {
        return rentalRepository.findByCustomerId(customerId);
    }

    // Filter by status (e.g., REQUESTED, RENTED, RETURNED)
    @GetMapping("/status/{status}")
    public List<Rental> getRentalsByStatus(@PathVariable String status) {
        return rentalRepository.findByStatusIgnoreCase(status);
    }
}
