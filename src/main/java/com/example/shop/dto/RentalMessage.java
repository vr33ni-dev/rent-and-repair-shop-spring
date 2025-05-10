package com.example.shop.dto;

import com.example.shop.model.Rental;

public class RentalMessage {
    private Long rentalId;
    private Long surfboardId;
    private Long userId;

    public RentalMessage(Rental rental) {
        this.rentalId = rental.getId();
        this.surfboardId = rental.getSurfboardId();
        this.userId = rental.getUserId();
    }
    public Long getRentalId() { return rentalId; }
    public void setRentalId(Long rentalId) { this.rentalId = rentalId; }
    public Long getSurfboardId() { return surfboardId; }
    public void setSurfboardId(Long surfboardId) { this.surfboardId = surfboardId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

}
