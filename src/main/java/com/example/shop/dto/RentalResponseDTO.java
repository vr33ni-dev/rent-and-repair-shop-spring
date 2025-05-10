package com.example.shop.dto;

import java.time.LocalDateTime;

public class RentalResponseDTO {
    private Long rentalId;
    private Long surfboardId;
    private String surfboardName;
    private String customerName; // "Shop" if shop-owned
    private LocalDateTime rentedAt;
    private LocalDateTime returnedAt;
    private String status;

    public RentalResponseDTO(Long rentalId, Long surfboardId, String surfboardName, String customerName,
            LocalDateTime rentedAt, LocalDateTime returnedAt, String status) {
        this.rentalId = rentalId;
        this.surfboardId = surfboardId;
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
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}
