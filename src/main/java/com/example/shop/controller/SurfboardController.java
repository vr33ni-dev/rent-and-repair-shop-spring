package com.example.shop.controller;

import com.example.shop.model.Surfboard;
import com.example.shop.repository.SurfboardRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/surfboards")
public class SurfboardController {

    private final SurfboardRepository surfboardRepository;

    public SurfboardController(SurfboardRepository surfboardRepository) {
        this.surfboardRepository = surfboardRepository;
    }

    @GetMapping("/all")
    public List<Surfboard> getAllSurfboards() {
        return surfboardRepository.findAll();
    }
}
