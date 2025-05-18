package com.example.shop.dto;

import java.util.UUID;

public class RentalRequest {
    private UUID surfboardId;
    private String customerName;
    private String customerContact;
    private Double rentalFee;

    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getRentalFee() {
        return rentalFee;
    }
    public void setRentalFee(Double rentalFee) {
        this.rentalFee = rentalFee;
    }
    public String getCustomerContact() {
        return customerContact;
    }
    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public UUID getSurfboardId() {
        return surfboardId;
    }  
    public void setSurfboardId(UUID surfboardId) {
        this.surfboardId = surfboardId;
    }
 }
