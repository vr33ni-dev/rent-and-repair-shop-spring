// src/main/java/com/example/shop/service/InventoryService.java
package com.example.shop.service;

import com.example.shop.dto.SurfboardRequest;
import com.example.shop.dto.SurfboardResponseDTO;
import com.example.shop.enums.RepairStatus;
import com.example.shop.model.Repair;
import com.example.shop.model.Surfboard;
import com.example.shop.repository.RepairRepository;
import com.example.shop.repository.SurfboardRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    private final SurfboardRepository surfboardRepository;
    private final RepairRepository repairRepository;

    public InventoryService(SurfboardRepository surfboardRepository, RepairRepository repairRepository) {
        this.repairRepository = repairRepository;
        this.surfboardRepository = surfboardRepository;
    }

    public List<SurfboardResponseDTO> getAllBoards() {
        return surfboardRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<SurfboardResponseDTO> getAvailableBoards() {
        return surfboardRepository.findAll().stream()
                .filter(Surfboard::isAvailableForRental)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public SurfboardResponseDTO createSurfboard(SurfboardRequest req) {
        // 1) Make the new board
        Surfboard board = new Surfboard();
        board.setName(req.getName());
        board.setDescription(req.getDescription());
        board.setSize(parseInches(req.getSizeText())); // parse size text to double
        board.setSizeText(req.getSizeText() == "" ? "-" : req.getSizeText());
        board.setShopOwned(true);
        board.setDamaged(req.isDamaged());
        board.setAvailable(!req.isDamaged()); // available only if not damaged
        board.setImageUrl(req.getImageUrl().isBlank() ? "" : req.getImageUrl());
        board = surfboardRepository.save(board);

        // 2) If it’s damaged, open a “Repair” for it right away
        if (req.isDamaged()) {
            Repair r = new Repair();
            r.setSurfboardId(board.getId());
            r.setIssue("New board arrived damaged");
            r.setStatus(RepairStatus.CREATED);
            r.setCreatedAt(LocalDateTime.now());
            repairRepository.save(r);
        }

        // 3) Return the board DTO
        return new SurfboardResponseDTO(
                board.getId(), board.getName(), board.getDescription(),
                board.getSize(), board.getSizeText(),
                board.isAvailable(), board.isDamaged(), board.isShopOwned(), board.getImageUrl(), board.getOwnerId());
    }

     public boolean deleteSurfboard(UUID id) {
        if (!surfboardRepository.existsById(id)) {
            return false;
        }
        surfboardRepository.deleteById(id);
        return true;
    }

    public boolean updateImageUrl(UUID id, String imageUrl) {
        return surfboardRepository.findById(id).map(board -> {
            board.setImageUrl(imageUrl);
            surfboardRepository.save(board);
            return true;
        }).orElse(false);
    }

    /**
     * Parse a feet-inches string like "5\"10" or "5\"2" into a Double
     * whose decimal part is literally the inches.
     * Returns null on null/blank or on any parse failure.
     */
    private Double parseInches(String txt) {
        if (txt == null || txt.isBlank())
            return null;
        // split on the quote:
        String[] parts = txt.split("\"", 2);
        if (parts.length != 2)
            return null;

        String feetStr = parts[0].trim();
        String inchStr = parts[1].trim();

        // guard against empties:
        if (feetStr.isEmpty() || inchStr.isEmpty())
            return null;

        try {
            // parse each piece as an integer:
            int feet = Integer.parseInt(feetStr);
            int inch = Integer.parseInt(inchStr);
            // build the feet.inch string, preserving leading zeros in inch if any:
            String decimalString = feet + "." + inch;
            return Double.parseDouble(decimalString);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private SurfboardResponseDTO toDto(Surfboard b) {
        // sizeValue may be null in the DTO if you choose
        return new SurfboardResponseDTO(
                b.getId(),
                b.getName(),
                b.getDescription(),
                b.getSize(), // wrapper
                b.getSizeText(),
                b.isAvailable(),
                b.isDamaged(),
                b.isShopOwned(),
                b.getImageUrl(),
                b.getOwnerId() // nullable for shop‐owned
        );
    }

}