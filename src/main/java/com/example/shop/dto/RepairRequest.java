package com.example.shop.dto;

public class RepairRequest {
    private Long surfboardId;
    private String issue;

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
}
