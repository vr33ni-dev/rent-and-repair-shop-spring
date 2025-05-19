package com.example.shop.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import com.example.shop.enums.BillStatus;

@Entity
public class Bill {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;
    private String description; // e.g., "Rental", "Repair"

    private UUID rentalId; // to associate with the rental
   
    private UUID repairId; // to associate with the repair
   
    private UUID customerId;
    private Double rentalFee;
    private Double repairFee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BillStatus status;

    private Double totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime paidAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getRentalId() {
        return rentalId;
    }

    public void setRentalId(UUID rentalId) {
        this.rentalId = rentalId;
    }

    public UUID getRepairId() {
        return repairId;
    }

    public void setRepairId(UUID repairId) {
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



    public LocalDateTime getPaidAt() {
        return paidAt;
    }
    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
