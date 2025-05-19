package com.example.shop.dto;

import java.time.LocalDateTime;
import java.util.UUID;

 
import com.example.shop.enums.BillStatus;
 
public class BillResponseDTO {
    private UUID id;
    private UUID customerId;
    private String customerName;
    private String customerContact;  
    private String customerContactType;
    private UUID rentalId;
    private UUID repairId;
    private double rentalFee;
    private double repairFee;
    private double totalAmount;
    private BillStatus status;
    private LocalDateTime billCreatedAt;
    private LocalDateTime billPaidAt;
    private LocalDateTime rentalDate;
    private LocalDateTime repairDate;
    private String description;

    public BillResponseDTO(UUID id, UUID customerId, String customerName, String customerContact, String customerContactType, String description,
    UUID rentalId, UUID repairId,
            double rentalFee, double repairFee, double totalAmount,
            BillStatus status, LocalDateTime billCreatedAt, LocalDateTime billPaidAt,
            LocalDateTime rentalDate, LocalDateTime repairDate) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerContact = customerContact;
        this.customerContactType = customerContactType;
        this.description = description;
        this.rentalId = rentalId;
        this.repairId = repairId;
        this.rentalFee = rentalFee;
        this.repairFee = repairFee;
        this.totalAmount = totalAmount;
        this.status = status;
        this.billCreatedAt = billCreatedAt;
        this.billPaidAt = billPaidAt;
        this.rentalDate = rentalDate;
        this.repairDate = repairDate;
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContact() {
        return customerContact;
    }
    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }
    public String getCustomerContactType() {
        return customerContactType;
    }
    public void setCustomerContactType(String customerContactType) {
        this.customerContactType = customerContactType;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getBillCreatedAt() {
        return billCreatedAt;
    }

    public void setBillCreatedAt(LocalDateTime billCreatedAt) {
        this.billCreatedAt = billCreatedAt;
    }

    public LocalDateTime getBillPaidAt() {
        return billPaidAt;
    }
    public void setBillPaidAt(LocalDateTime billPaidAt) {
        this.billPaidAt = billPaidAt;
    }
    public LocalDateTime getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDateTime rentalDate) {
        this.rentalDate = rentalDate;
    }

    public LocalDateTime getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(LocalDateTime repairDate) {
        this.repairDate = repairDate;
    }

public BillStatus getStatus() {
        return status;
    }
    public void setStatus(BillStatus status) {
        this.status = status;
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

    public double getRentalFee() {
        return rentalFee;
    }

    public void setRentalFee(double rentalFee) {
        this.rentalFee = rentalFee;
    }

    public double getRepairFee() {
        return repairFee;
    }

    public void setRepairFee(double repairFee) {
        this.repairFee = repairFee;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

}
