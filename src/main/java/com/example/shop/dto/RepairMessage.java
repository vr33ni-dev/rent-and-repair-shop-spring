package com.example.shop.dto;

import java.util.UUID;

public class RepairMessage {
    
    private UUID surfboardId;
    private String issue;
    private UUID customerId; 
    private UUID rentalId;
    private Double repairFee;

    public RepairMessage() {}

    public RepairMessage(UUID surfboardId, String issue, UUID customerId, UUID rentalId, Double repairFee) {
        this.surfboardId = surfboardId;
        this.issue = issue;
        this.customerId = customerId;
        this.rentalId = rentalId;
        this.repairFee = repairFee;
    }

    public UUID getSurfboardId() {
        return surfboardId;
    }

    public void setSurfboardId(UUID surfboardId) {
        this.surfboardId = surfboardId;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getRentalId() {
        return rentalId;
    }       
    
    public void setRentalId(UUID rentalId) {
        this.rentalId = rentalId;
    }
    public Double getRepairFee() {
        return repairFee;
    }   
    public void setRepairFee(Double repairFee) {
        this.repairFee = repairFee;
    }
}
