// src/main/java/com/example/shop/service/InventoryService.java
package com.example.shop.service;

import com.example.shop.dto.SurfboardRequest;
import com.example.shop.dto.SurfboardResponseDTO;
import com.example.shop.model.Surfboard;
import com.example.shop.repository.SurfboardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    private final SurfboardRepository surfboardRepository;

    public InventoryService(SurfboardRepository surfboardRepository) {
        this.surfboardRepository = surfboardRepository;
    }

    @Transactional
    public SurfboardResponseDTO createSurfboard(SurfboardRequest req) {
        Surfboard board = new Surfboard();
        board.setName(req.getName());
        board.setDescription(req.getDescription());
        board.setShopOwned(true);
        board.setDamaged(req.isDamaged());
        board.setAvailable(!req.isDamaged());
        board.setImageUrl(req.getImageUrl());

        Surfboard saved = surfboardRepository.save(board);
        return new SurfboardResponseDTO(
            saved.getId(),
            saved.getName(),
            saved.getDescription(),
            saved.isAvailable(),
            saved.isDamaged(),
            saved.isShopOwned(),
            saved.getImageUrl()
        );
    }
}
