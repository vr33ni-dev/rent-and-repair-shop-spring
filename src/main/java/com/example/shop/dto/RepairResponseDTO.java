package com.example.shop.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.shop.enums.RepairStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RepairResponseDTO {
    private UUID repairId;
    private UUID surfboardId;
    private UUID customerId;
    private UUID rentalId;
    private String surfboardName;
    private String customerName; // "Shop" if shop-owned
    private String issue;
    private RepairStatus status;
    private LocalDateTime createdAt;

     public RepairResponseDTO(UUID repairId, UUID surfboardId, UUID customerId, UUID rentalId, String surfboardName, 
            String issue, RepairStatus status, LocalDateTime createdAt, String customerName) {
        this.repairId = repairId;
        this.surfboardId = surfboardId;
        this.customerId = customerId;
        this.rentalId = rentalId;;
        this.surfboardName = surfboardName;
        this.customerName = customerName;
        this.issue = issue;
        this.status = status;
        this.createdAt = createdAt;
    }

    public UUID getRepairId() {
        return repairId;
    }

    public void setRepairId(UUID repairId) {
        this.repairId = repairId;
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

    public UUID getRentalId() {
        return rentalId;
    }
    public void setRentalId(UUID rentalId) {
        this.rentalId = rentalId;
    }
 
    public String getSurfboardName() {
        return surfboardName;
    }

    public void setSurfboardName(String boardName) {
        this.surfboardName = boardName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getIssue() {
        return issue;
    }
    public void setIssue(String issue) {
        this.issue = issue;
    }
    public RepairStatus getStatus() {
        return status;
    }
    public void setStatus(RepairStatus status) {
        this.status = status;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
