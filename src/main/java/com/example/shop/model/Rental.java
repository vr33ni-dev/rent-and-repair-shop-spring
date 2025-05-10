package com.example.shop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Rental {

    @Id
    @GeneratedValue
    private Long id;

    private Long surfboardId;
    private Long customerId;
    private LocalDateTime rentedAt;
    private LocalDateTime returnedAt;
    private String status;

    public Long getId() { return id; }
    public Long getSurfboardId() { return surfboardId; }
    public Long getCustomerId() { return customerId; }
    public LocalDateTime getRentedAt() { return rentedAt; }
    public LocalDateTime getReturnedAt() { return returnedAt; }
    public String getStatus() { return status; }
    public void setSurfboardId(Long surfboardId) { this.surfboardId = surfboardId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }    
    public void setRentedAt(LocalDateTime rentedAt) { this.rentedAt = rentedAt; }
    public void setReturnedAt(LocalDateTime returnedAt) { this.returnedAt = returnedAt; }
    public void setStatus(String status) { this.status = status; }
    public void setId(Long id) { this.id = id; }
 }
