package com.example.shop.dto;

public class RepairMessage {
    private Long surfboardId;
    private String issue;

    public RepairMessage(Long surfboardId, String issue) {
        this.surfboardId = surfboardId;
        this.issue = issue;
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

 }
