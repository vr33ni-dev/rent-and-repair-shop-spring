package com.example.shop.model;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Surfboard {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    private String name;

    @Column(columnDefinition = "TEXT") // optional, allow longer descriptions
    private String description;

    private String sizeText;   // e.g. "6\"0" or "6.0"
    private Double size;       // parsed inches, e.g. 6.0

    private boolean available;
    private boolean damaged;
    private boolean shopOwned;
    private String imageUrl;

    private UUID ownerId; // nullable if shop-owned

    public Surfboard() {
    }

 
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getSizeText() { return sizeText; }
    public void setSizeText(String sizeText) { this.sizeText = sizeText; }

    public Double getSize() {
        return size;
    }
    public void setSize(Double size) {
        this.size = size;
    }
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isAvailableForRental() {
        return shopOwned && available && !damaged;
    }

    public boolean isDamaged() {
        return damaged;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

    public boolean isShopOwned() {
        return shopOwned;
    }

    public void setShopOwned(boolean shopOwned) {
        this.shopOwned = shopOwned;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Surfboard))
            return false;
        Surfboard that = (Surfboard) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
