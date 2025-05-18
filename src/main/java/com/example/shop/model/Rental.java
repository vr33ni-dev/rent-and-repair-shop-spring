package com.example.shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import com.example.shop.enums.RentalStatus;

@Entity
public class Rental {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;
  
    private UUID surfboardId;

    private UUID customerId;
    private LocalDateTime rentedAt;
    private LocalDateTime returnedAt;
    private Double rentalFee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RentalStatus status;

 public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public UUID getSurfboardId() {
        return surfboardId;
    }

    public Double getRentalFee() {
        return rentalFee;
    }

    public void setRentalFee(Double rentalFee) {
        this.rentalFee = rentalFee;
    }

    public UUID getCustomerId() {
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

    public void setSurfboardId(UUID surfboardId) {
        this.surfboardId = surfboardId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public void setRentedAt(LocalDateTime rentedAt) {
        this.rentedAt = rentedAt;
    }

    public void setReturnedAt(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }

}
