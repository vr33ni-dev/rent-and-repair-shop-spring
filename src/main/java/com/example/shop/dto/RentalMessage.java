package com.example.shop.dto;

import java.util.UUID;

public class RentalMessage   {
    private UUID rentalId;
    private UUID surfboardId;
    private UUID customerId;
    private boolean damaged;
    private Double rentalFee;

    public RentalMessage() {}         // Required for JSON deserialization

    public RentalMessage(UUID rentalId, UUID surfboardId, UUID customerId, boolean damaged, Double rentalFee) {
        this.rentalId = rentalId;
        this.surfboardId = surfboardId;
        this.customerId = customerId;
        this.damaged = damaged;
        this.rentalFee = rentalFee;
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

    public boolean isDamaged() {
        return damaged;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }
    public Double getRentalFee() {
        return rentalFee;
    }
    public void setRentalFee(Double rentalFee) {
        this.rentalFee = rentalFee;
    }
}
