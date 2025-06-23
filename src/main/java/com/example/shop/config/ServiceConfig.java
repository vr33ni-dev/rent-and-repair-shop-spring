
package com.example.shop.config;

import com.example.shop.repository.*;
import com.example.shop.service.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!keepalive")  // Load only when NOT in keepalive mode
public class ServiceConfig {

    @Bean
    public BillingService billingService(
        BillingRepository billingRepository,
        CustomerRepository customerRepository,
        RepairRepository repairRepository,
        RentalRepository rentalRepository
    ) {
        return new BillingService(billingRepository, customerRepository, repairRepository, rentalRepository);
    }

    @Bean
    public RepairService repairService(
        RepairRepository repairRepository,
        CustomerRepository customerRepository
    ) {
        return new RepairService(repairRepository, null, customerRepository, null, null);
    }

    @Bean
    public RentalService rentalService(
        RentalRepository rentalRepository,
        CustomerRepository customerRepository
    ) {
        return new RentalService(rentalRepository, null, customerRepository, null, null, null);
    }

    @Bean
    public SettingsService settingsService(SettingsRepository settingsRepository) {
        return new SettingsService(settingsRepository);
    }

    @Bean
    public InventoryService inventoryService(
        SurfboardRepository surfboardRepository,
        RepairRepository repairRepository
    ) {
        return new InventoryService(surfboardRepository, repairRepository);
    }

 }
