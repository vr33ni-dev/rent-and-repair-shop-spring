package com.example.shop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Repair {

    @Id
    @GeneratedValue
    private Long id;
    private Long userId; // Ensure this field exists

    private Long surfboardId;
    private String issue;
    private String status;

    public Long getId() { return id; }
    public Long getUserId() { return userId; } 
    public Long getSurfboardId() { return surfboardId; }
    public String getIssue() { return issue; }
    public String getStatus() { return status; }
    public void setId(Long id) { this.id = id; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setSurfboardId(Long surfboardId) { this.surfboardId = surfboardId; }
    public void setIssue(String issue) { this.issue = issue; }
    public void setStatus(String status) { this.status = status; }

 }
