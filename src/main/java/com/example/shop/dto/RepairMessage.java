package com.example.shop.dto;

public class RepairMessage {
    private Long surfboardId;
    private String issue;
    private Long customerId; 

    public RepairMessage() {}

    public RepairMessage(Long surfboardId, String issue, Long customerId) {
        this.surfboardId = surfboardId;
        this.issue = issue;
        this.customerId = customerId;
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
}
