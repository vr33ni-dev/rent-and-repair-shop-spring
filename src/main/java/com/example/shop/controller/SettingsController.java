package com.example.shop.controller;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.model.Setting;
import com.example.shop.service.SettingsService;

@RestController
@RequestMapping("/api/settings")
public class SettingsController {
    private final SettingsService service;

    public SettingsController(SettingsService service) {
        this.service = service;
    }

    @GetMapping
    public Map<String, Object> getSettings() {
        return Map.of("defaultRentalFee", service.getDefaultRentalFee());
    }

    @PutMapping("/{key}")
    public ResponseEntity<?> updateSetting(
            @PathVariable String key,
            @RequestBody Map<String, String> body) {
        String newValue = body.get("value");
        if (newValue == null) {
            return ResponseEntity.badRequest().body("Missing 'value'");
        }

        Optional<Setting> settingOpt = service.getByKey(key);

        if (settingOpt.isPresent()) {
            Setting setting = settingOpt.get();
            setting.setValue(newValue);
            service.save(setting);
            return ResponseEntity.ok().build();
        } else {
            // Optional: create if not exists
            Setting setting = new Setting();
            setting.setId(UUID.randomUUID());
            setting.setKey(key);
            setting.setValue(newValue);
            service.save(setting);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }

}
