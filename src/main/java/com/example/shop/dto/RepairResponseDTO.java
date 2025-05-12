package com.example.shop.dto;

import java.time.LocalDateTime;

import com.example.shop.enums.RepairStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RepairResponseDTO {
    private Long repairId;
    private Long surfboardId;
    private Long customerId;
    private Long rentalId;
    private String surfboardName;
    private String customerName; // "Shop" if shop-owned
    private String issue;
    private RepairStatus status;
    private LocalDateTime createdAt;

     public RepairResponseDTO(Long repairId, Long surfboardId, Long customerId, Long rentalId, String surfboardName, 
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

    public Long getRepairId() {
        return repairId;
    }

    public void setRepairId(Long repairId) {
        this.repairId = repairId;
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

    public Long getRentalId() {
        return rentalId;
    }
    public void setRentalId(Long rentalId) {
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
