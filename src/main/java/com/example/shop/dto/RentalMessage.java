package com.example.shop.dto;

 
public class RentalMessage   {
    private Long rentalId;
    private Long surfboardId;
    private Long customerId;
    private boolean damaged;

    public RentalMessage() {}         // Required for JSON deserialization

    public RentalMessage(Long rentalId, Long surfboardId, Long customerId, boolean damaged) {
        this.rentalId = rentalId;
        this.surfboardId = surfboardId;
        this.customerId = customerId;
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setUserId(Long customerId) {
        this.customerId = customerId;
    }

    public boolean isDamaged() {
        return damaged;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

}
