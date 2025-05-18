package com.example.shop.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.shop.enums.RentalStatus;

public class RentalResponseDTO {
    private UUID rentalId;
    private UUID surfboardId;
    private UUID customerId;
    private Double rentalFee;
    private String surfboardName;
    private String customerName;
    private LocalDateTime rentedAt;
    private LocalDateTime returnedAt;
    private RentalStatus status;

    public RentalResponseDTO(UUID rentalId, UUID surfboardId, UUID customerId, Double rentalFee, String surfboardName, String customerName,
            LocalDateTime rentedAt, LocalDateTime returnedAt, RentalStatus status) {
        this.rentalId = rentalId;
        this.surfboardId = surfboardId;
        this.customerId = customerId;
        this.rentalFee = rentalFee;
        this.surfboardName = surfboardName;
        this.customerName = customerName;
        this.rentedAt = rentedAt;
        this.returnedAt = returnedAt;
        this.status = status;
    }

    public UUID getRentalId() {
        return rentalId;
    }

    public void setRentalId(UUID rentalId) {
        this.rentalId = rentalId;
    }

    public UUID getSurfboardId() {
        return surfboardId;
    }

    public void setSurfboardId(UUID surfboardId) {
        this.surfboardId = surfboardId;
    }

    public UUID getCustomerId() {
        return customerId;
    }
    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public Double getRentalFee() {
        return rentalFee;
    }
    public void setRentalFee(Double rentalFee) {
        this.rentalFee = rentalFee;
    }
    public String getSurfboardName() {
        return surfboardName;
    }

    public void setSurfboardName(String surfboardName) {
        this.surfboardName = surfboardName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getRentedAt() {
        return rentedAt;
    }

    public void setRentedAt(LocalDateTime rentedAt) {
        this.rentedAt = rentedAt;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    public void setReturnedAt(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }

    public RentalStatus getStatus() {
        return status;
    }

    public void setStatus(RentalStatus status) {
        this.status = status;
    }

    
}
