package com.example.shop.dto;

import java.time.LocalDateTime;

import com.example.shop.enums.RentalStatus;

public class RentalResponseDTO {
    private Long rentalId;
    private Long surfboardId;
    private Long customerId;
    private String surfboardName;
    private String customerName;
    private LocalDateTime rentedAt;
    private LocalDateTime returnedAt;
    private RentalStatus status;

    public RentalResponseDTO(Long rentalId, Long surfboardId, Long customerId, String surfboardName, String customerName,
            LocalDateTime rentedAt, LocalDateTime returnedAt, RentalStatus status) {
        this.rentalId = rentalId;
        this.surfboardId = surfboardId;
        this.customerId = customerId;
        this.surfboardName = surfboardName;
        this.customerName = customerName;
        this.rentedAt = rentedAt;
        this.returnedAt = returnedAt;
        this.status = status;
    }

    public Long getRentalId() {
        return rentalId;
    }

    public void setRentalId(Long rentalId) {
        this.rentalId = rentalId;
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
