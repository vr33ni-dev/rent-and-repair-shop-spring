package com.example.shop.dto;

import java.util.UUID;

public class RepairRequest {
    private UUID surfboardId;
    private String issue;
    private String customerName;
    private String customerContact;

    public UUID getSurfboardId() {
        return surfboardId;
    }

    public void setSurfboardId(UUID surfboardId) {
        this.surfboardId = surfboardId;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
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
