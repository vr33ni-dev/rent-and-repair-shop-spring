package com.example.shop.dto;

import java.util.UUID;

public class RepairRequest {
    private String customerName;
    private String customerContact;
    private UUID surfboardId; // shop-owned board
    private String surfboardName; // customer’s board new record
    private String issue;
    private Double repairFee;

    public UUID getSurfboardId() {
        return surfboardId;
    }

    public void setSurfboardId(UUID surfboardId) {
        this.surfboardId = surfboardId;
    }

    public RepairRequest() {
    }

    public RepairRequest(String customerName, String customerContact, UUID surfboardId, String surfboardName,
            String issue, Double repairFee) {
        this.customerName = customerName;
        this.customerContact = customerContact;
        this.surfboardId = surfboardId;
        this.surfboardName = surfboardName;
        this.issue = issue;
        this.repairFee = repairFee;
    }

    public String getSurfboardName() {
        return surfboardName;
    }

    public void setSurfboardName(String surfboardName) {
        this.surfboardName = surfboardName;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public Double getRepairFee() {
        return repairFee;
    }

    public void setRepairFee(Double repairFee) {
        this.repairFee = repairFee;
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

}
