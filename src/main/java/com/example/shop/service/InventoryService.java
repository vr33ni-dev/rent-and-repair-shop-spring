package com.example.shop.service;


import com.example.shop.repository.SurfboardRepository;

 import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final SurfboardRepository surfboardRepository;

    public InventoryService(SurfboardRepository surfboardRepository) {
        this.surfboardRepository = surfboardRepository;
    }

}
