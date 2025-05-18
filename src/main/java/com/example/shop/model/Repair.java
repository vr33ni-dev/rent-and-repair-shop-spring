package com.example.shop.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    private UUID customerId;  

    private UUID rentalId;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    private UUID surfboardId; // show owned board - one of these will be non null

    private String issue;
    private Double repairFee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RepairStatus status;

    public UUID getId() {
        return id;
    }


    public Double getRepairFee() {
        return repairFee;
    }
    public void setRepairFee(Double repairFee) {
        this.repairFee = repairFee;
    }
    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getRentalId() {
        return rentalId;
    }

    public void setRentalId(UUID rentalId) {
        this.rentalId = rentalId;
    }

    public UUID getSurfboardId() {
        return surfboardId;
    }

    public String getIssue() {
        return issue;
    }
 

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public void setSurfboardId(UUID surfboardId) {
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
