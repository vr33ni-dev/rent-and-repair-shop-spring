package com.example.shop.dto;

public class RentalRequest {
    private Long customerId;
    private Long surfboardId;


    public Long getCustomerId() {
        return customerId;
    }
    public void setUserId(Long customerId) {
        this.customerId = customerId;
    }
    public Long getSurfboardId() {
        return surfboardId;
    }  
    public void setSurfboardId(Long surfboardId) {
        this.surfboardId = surfboardId;
    }
 }
