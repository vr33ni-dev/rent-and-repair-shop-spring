package com.example.shop.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.shop.model.Setting;
import com.example.shop.repository.SettingsRepository;

@Service
public class SettingsService {
    private final SettingsRepository repo;

    public SettingsService(SettingsRepository repo) {
        this.repo = repo;
    }

    public double getDefaultRentalFee() {
        return repo.findByKey("default_rental_fee")
                   .map(s -> Double.parseDouble(s.getValue()))
                   .orElse(15.0); // fallback
    }

    public Optional<Setting> getByKey(String key) {
    return repo.findByKey(key);
}

public Setting save(Setting setting) {
    return repo.save(setting);
}

}
