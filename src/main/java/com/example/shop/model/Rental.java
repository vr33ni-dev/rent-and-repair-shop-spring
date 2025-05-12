package com.example.shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

import com.example.shop.enums.RentalStatus;

@Entity
public class Rental {

    @Id
    @GeneratedValue
    private Long id;

    private Long surfboardId;
    private Long customerId;
    private LocalDateTime rentedAt;
    private LocalDateTime returnedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RentalStatus status;

    public Long getId() {
        return id;
    }

    public Long getSurfboardId() {
        return surfboardId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public LocalDateTime getRentedAt() {
        return rentedAt;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    public RentalStatus getStatus() {
        return status;
    }

    public void setStatus(RentalStatus status) {
        this.status = status;
    }
    public void setSurfboardId(Long surfboardId) {
        this.surfboardId = surfboardId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setRentedAt(LocalDateTime rentedAt) {
        this.rentedAt = rentedAt;
    }

    public void setReturnedAt(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }

 
    public void setId(Long id) {
        this.id = id;
    }
}
