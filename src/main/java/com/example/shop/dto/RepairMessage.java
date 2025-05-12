package com.example.shop.dto;

public class RepairMessage {
    
    private Long surfboardId;
    private String issue;
    private Long customerId; 
    private Long rentalId;

    public RepairMessage() {}

    public RepairMessage(Long surfboardId, String issue, Long customerId, Long rentalId) {
        this.surfboardId = surfboardId;
        this.issue = issue;
        this.customerId = customerId;
        this.rentalId = rentalId;
    }

    public Long getSurfboardId() {
        return surfboardId;
    }

    public void setSurfboardId(Long surfboardId) {
        this.surfboardId = surfboardId;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
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
}
