package com.example.shop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Surfboard {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private boolean available;
    private boolean damaged;

    private boolean shopOwned;     // True = rentable, False = user-owned
    private Long ownerUserId;      // Nullable – only set for user-owned boards

    // Constructors
    public Surfboard() {}
    public Surfboard(String name, boolean available, boolean damaged, boolean shopOwned, Long ownerUserId) {
        this.name = name;
        this.available = available;
        this.damaged = damaged;
        this.shopOwned = shopOwned;
        this.ownerUserId = ownerUserId;
    }

    // Getters and Setters...
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
        return this.ownerUserId == null && this.available && !this.damaged;
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
    public Long getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }
    
}
