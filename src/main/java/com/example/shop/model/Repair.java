package com.example.shop.model;

import java.time.LocalDateTime;

import com.example.shop.enums.RepairStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Repair {

    @Id
    @GeneratedValue
    private Long id;
    private Long customerId;  
    private Long rentalId;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private Long surfboardId;
    private String issue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RepairStatus status;

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getRentalId() {
        return rentalId;
    }

    public void setRentalId(Long rentalId) {
        this.rentalId = rentalId;
    }

    public Long getSurfboardId() {
        return surfboardId;
    }

    public String getIssue() {
        return issue;
    }
 

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setSurfboardId(Long surfboardId) {
        this.surfboardId = surfboardId;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

   

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public RepairStatus getStatus() {
        return status;
    }
    public void setStatus(RepairStatus status) {
        this.status = status;
    }
}
