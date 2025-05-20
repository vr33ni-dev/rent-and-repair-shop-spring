// src/main/java/com/example/shop/dto/SurfboardResponseDTO.java
package com.example.shop.dto;

import java.util.UUID;

public class SurfboardResponseDTO {
    private UUID id;
    private String name;
    private String description;
    private boolean available;
    private boolean damaged;
    private boolean shopOwned;
    private String imageUrl;

    public SurfboardResponseDTO(UUID id, String name, String description,
                                boolean available, boolean damaged,
                                boolean shopOwned, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.damaged = damaged;
        this.shopOwned = shopOwned;
        this.imageUrl = imageUrl;
    }

    // getters only
    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public boolean isAvailable() { return available; }
    public boolean isDamaged() { return damaged; }
    public boolean isShopOwned() { return shopOwned; }
    public String getImageUrl() { return imageUrl; }
}
    