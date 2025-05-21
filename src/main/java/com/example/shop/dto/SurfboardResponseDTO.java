// src/main/java/com/example/shop/dto/SurfboardResponseDTO.java
package com.example.shop.dto;

import java.util.UUID;

public class SurfboardResponseDTO {
    private UUID id;
    private String name;
    private String description;
    private Double size;
    private String sizeText;
    private boolean available;
    private boolean damaged;
    private boolean shopOwned;
    private String imageUrl;
    private UUID ownerId; // nullable if shop-owned

    public SurfboardResponseDTO(UUID id, String name, String description, Double size, String sizeText,
            boolean available, boolean damaged,
            boolean shopOwned, String imageUrl, UUID ownerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.size = size;
        this.sizeText = sizeText;
        this.available = available;
        this.damaged = damaged;
        this.shopOwned = shopOwned;
        this.imageUrl = imageUrl;
        this.ownerId = ownerId;
    }

    // getters only
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getSize() {
        return size;
    }

    public String getSizeText() {
        return sizeText;
    }
    public boolean isAvailable() {
        return available;
    }

    public boolean isDamaged() {
        return damaged;
    }

    public boolean isShopOwned() {
        return shopOwned;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public UUID getOwnerId() {
        return ownerId;
    }
}
