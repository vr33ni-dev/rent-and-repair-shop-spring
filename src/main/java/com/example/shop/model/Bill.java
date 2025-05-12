package com.example.shop.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.example.shop.enums.BillStatus;

@Entity
public class Bill {

    @Id
    @GeneratedValue
    private Long id;
    private String description; // e.g., "Rental", "Repair"
    private Long rentalId; // to associate with the rental
    private Long repairId; // to associate with the repair
    private Long customerId;
    private Double rentalFee;
    private Double repairFee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BillStatus status;

    private Double totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
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

    public Long getRepairId() {
        return repairId;
    }

    public void setRepairId(Long repairId) {
        this.repairId = repairId;
    }

    public Double getRentalFee() {
        return rentalFee;
    }

    public void setRentalFee(Double rentalFee) {
        this.rentalFee = rentalFee;
    }

    public Double getRepairFee() {
        return repairFee;
    }

    public void setRepairFee(Double repairFee) {
        this.repairFee = repairFee;
    }

    public BillStatus getStatus() {
        return status;
    }

    public void setStatus(BillStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
