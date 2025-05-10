package com.example.shop.dto;

public class RepairMessage {
    private Long surfboardId;
    private String issue;
    private Long userId; // Optional: null if shop-owned

    public RepairMessage() {}

    public RepairMessage(Long surfboardId, String issue, Long userId) {
        this.surfboardId = surfboardId;
        this.issue = issue;
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
