package com.example.shop.dto;

public class RentalRequest {
    private Long userId;
    private Long surfboardId;


    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getSurfboardId() {
        return surfboardId;
    }  
    public void setSurfboardId(Long surfboardId) {
        this.surfboardId = surfboardId;
    }
 }
