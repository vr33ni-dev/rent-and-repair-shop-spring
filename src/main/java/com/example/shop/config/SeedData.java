package com.example.shop.config;

import com.example.shop.model.Surfboard;
import com.example.shop.model.Rental;
import com.example.shop.model.Repair;
import com.example.shop.repository.SurfboardRepository;
import com.example.shop.repository.RentalRepository;
import com.example.shop.repository.RepairRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SeedData {

    private final SurfboardRepository surfboardRepo;
    private final RentalRepository rentalRepo;
    private final RepairRepository repairRepo;

    public SeedData(
        SurfboardRepository surfboardRepo,
        RentalRepository rentalRepo,
        RepairRepository repairRepo
    ) {
        this.surfboardRepo = surfboardRepo;
        this.rentalRepo = rentalRepo;
        this.repairRepo = repairRepo;
    }
    @PostConstruct
    public void init() {
        if (surfboardRepo.count() == 0) {
            // 🌊 Shop-owned surfboards
            Surfboard shopBoard1 = new Surfboard("Longboard Classic", true, false, true, null); // available, not damaged
            Surfboard shopBoard2 = new Surfboard("Shortboard Pro", false, true, true, null);   // damaged, not available
            Surfboard shopBoard3 = new Surfboard("Funboard XL", false, false, true, null);     // rented, not available
    
            shopBoard1 = surfboardRepo.save(shopBoard1);
            shopBoard2 = surfboardRepo.save(shopBoard2);
            shopBoard3 = surfboardRepo.save(shopBoard3);
             
            // 📦 Rentals
            Rental rental1 = new Rental();
            rental1.setSurfboardId(shopBoard3.getId());
            rental1.setUserId(42L);
            rental1.setRentedAt(LocalDateTime.now().minusDays(1));
            rental1.setStatus("RENTED");
            rentalRepo.save(rental1);
    
            // 🛠 Repairs
            Repair repair1 = new Repair();
            repair1.setSurfboardId(shopBoard2.getId());
            repair1.setIssue("Cracked fin");
            repair1.setStatus("IN_PROGRESS");
            repairRepo.save(repair1);
    
            Repair repair2 = new Repair();
            repair2.setSurfboardId(null); // it's not in shop inventory
            repair2.setUserId(101L);
            repair2.setIssue("Tail damage");
            repair2.setStatus("CREATED");
            repairRepo.save(repair2);
    
            System.out.println("🌱 Seed data loaded successfully");
        }
    }
    
}
