package com.example.shop.dto;

 
public class RentalMessage   {
    private Long rentalId;
    private Long surfboardId;
    private Long userId;
    private boolean damaged;

    public RentalMessage() {}         // Required for JSON deserialization

    public RentalMessage(Long rentalId, Long surfboardId, Long userId, boolean damaged) {
        this.rentalId = rentalId;
        this.surfboardId = surfboardId;
        this.userId = userId;
        this.damaged = damaged;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isDamaged() {
        return damaged;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

}
