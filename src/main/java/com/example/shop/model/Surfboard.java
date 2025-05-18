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
    private boolean available;
    private boolean damaged;
    private boolean shopOwned;
    private String imageUrl;
  
    private UUID ownerId; // nullable if shop-owned

    public Surfboard() {}

    public Surfboard(String name, boolean available, boolean damaged, boolean shopOwned, UUID ownerId, String imageUrl) {
        this.name = name;
        this.available = available;
        this.damaged = damaged;
        this.shopOwned = shopOwned;
        this.ownerId = ownerId;
        this.imageUrl = imageUrl;
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
        if (this == o) return true;
        if (!(o instanceof Surfboard)) return false;
        Surfboard that = (Surfboard) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
