package com.example.shop.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Surfboard {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private boolean available;
    private boolean damaged;
    private boolean shopOwned;
    private String imageUrl;
    private Long ownerId; // nullable if shop-owned

    public Surfboard() {}

    public Surfboard(String name, boolean available, boolean damaged, boolean shopOwned, Long ownerId, String imageUrl) {
        this.name = name;
        this.available = available;
        this.damaged = damaged;
        this.shopOwned = shopOwned;
        this.ownerId = ownerId;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(Long ownerId) {
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
