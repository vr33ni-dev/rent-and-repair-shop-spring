package com.example.shop.dto;

import java.time.LocalDateTime;

public class BillResponseDTO {
    private Long id;
    private Long surfboardId;
    private Long customerId;
    private String customerName;
    private double amount;
    private String description;
    private LocalDateTime billCreatedAt;     // when the bill was generated
    private LocalDateTime originDate;        // when the rental or repair happened

    public BillResponseDTO(Long id, Long surfboardId, Long customerId, String customerName,
                           double amount, String description, LocalDateTime billCreatedAt, LocalDateTime originDate) {
        this.id = id;
        this.surfboardId = surfboardId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.amount = amount;
        this.description = description;
        this.billCreatedAt = billCreatedAt;
        this.originDate = originDate;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getSurfboardId() { return surfboardId; }
    public void setSurfboardId(Long surfboardId) { this.surfboardId = surfboardId; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getBillCreatedAt() { return billCreatedAt; }
    public void setBillCreatedAt(LocalDateTime billCreatedAt) { this.billCreatedAt = billCreatedAt; }

    public LocalDateTime getOriginDate() { return originDate; }
    public void setOriginDate(LocalDateTime originDate) { this.originDate = originDate; }
}
