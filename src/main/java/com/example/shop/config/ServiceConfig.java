
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
    SurfboardRepository surfboardRepository,
    CustomerRepository customerRepository,
    BillingRepository billingRepository,
    RentalRepository rentalRepository  
) {
    return new RepairService(
        repairRepository,
        surfboardRepository,
        customerRepository,
        billingRepository,
        rentalRepository
    );
}


@Bean
public RentalService rentalService(
    RentalRepository rentalRepository,
    SurfboardRepository surfboardRepository,
    CustomerRepository customerRepository,
    BillingRepository billingRepository,
    RepairRepository repairRepository,
    SettingsService settingsService
) {
    return new RentalService(
        rentalRepository,
        surfboardRepository,
        customerRepository,
        billingRepository,
        repairRepository,
        settingsService
    );
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
