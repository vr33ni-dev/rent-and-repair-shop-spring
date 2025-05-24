package com.example.shop.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shop.model.Setting;

public interface SettingsRepository extends JpaRepository<Setting, UUID> {
    Optional<Setting> findByKey(String key);
}
