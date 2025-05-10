package com.example.shop.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Repair {

    @Id
    @GeneratedValue
    private Long id;
    private Long customerId; // Ensure this field exists
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private Long surfboardId;
    private String issue;
    private String status;

    public Long getId() { return id; }
    public Long getCustomerId() { return customerId; } 
    public Long getSurfboardId() { return surfboardId; }
    public String getIssue() { return issue; }
    public String getStatus() { return status; }
    public void setId(Long id) { this.id = id; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public void setSurfboardId(Long surfboardId) { this.surfboardId = surfboardId; }
    public void setIssue(String issue) { this.issue = issue; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
 }
