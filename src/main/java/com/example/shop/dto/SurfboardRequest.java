package com.example.shop.dto;

import jakarta.validation.constraints.NotBlank;

public class SurfboardRequest {
    @NotBlank
    private String name;

    private String description;
    private boolean damaged;
    private String imageUrl;

    public SurfboardRequest() {}

    // getters & setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isDamaged() { return damaged; }
    public void setDamaged(boolean damaged) { this.damaged = damaged; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
