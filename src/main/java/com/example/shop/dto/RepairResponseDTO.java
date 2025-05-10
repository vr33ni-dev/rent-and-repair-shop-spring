package com.example.shop.dto;

import java.time.LocalDateTime;

public class RepairResponseDTO {
    private Long repairId;
    private Long surfboardId;
    private String surfboardName;
    private String customerName; // "Shop" if shop-owned
    private String issue;
    private String status;
    private LocalDateTime createdAt;

    public RepairResponseDTO(Long repairId, Long surfboardId, String surfboardName, 
            String issue, String status, LocalDateTime createdAt, String customerName) {
        this.repairId = repairId;
        this.surfboardId = surfboardId;
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
    public String getSurfboardIdString() {
        return String.valueOf(surfboardId);
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
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
