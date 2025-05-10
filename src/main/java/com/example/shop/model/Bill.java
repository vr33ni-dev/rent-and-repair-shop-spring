package com.example.shop.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Bill {

    @Id
    @GeneratedValue
    private Long id;

    private Long surfboardId;
    private Long customerId;
    private double amount;
    private String description;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getSurfboardId() {
        return surfboardId;
    } 
    public void setSurfboardId(Long surfboardId) {
        this.surfboardId = surfboardId;
    }
    public Long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
}
