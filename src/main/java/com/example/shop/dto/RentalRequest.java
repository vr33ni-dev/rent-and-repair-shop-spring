package com.example.shop.dto;

public class RentalRequest {
    private Long surfboardId;
    private String customerName;
    private String customerContact;

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

    public Long getSurfboardId() {
        return surfboardId;
    }  
    public void setSurfboardId(Long surfboardId) {
        this.surfboardId = surfboardId;
    }
 }
