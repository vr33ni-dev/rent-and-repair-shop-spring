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
            Surfboard shopBoard1 = surfboardRepo.save(new Surfboard("Longboard Classic", true, false, true, null));
            Surfboard shopBoard2 = surfboardRepo.save(new Surfboard("Shortboard Pro", false, true, true, null)); // damaged
            Surfboard shopBoard3 = surfboardRepo.save(new Surfboard("Funboard XL", false, false, true, null)); // out for rent

            // 🏄 User-owned surfboards
            Surfboard userBoard1 = surfboardRepo.save(new Surfboard("Vintage Malibu", false, true, false, 101L));
            Surfboard userBoard2 = surfboardRepo.save(new Surfboard("Personal MiniMal", false, false, false, 202L));

            // 📦 Rentals (shop boards only)
            Rental rental1 = new Rental();
            rental1.setSurfboardId(shopBoard3.getId());
            rental1.setUserId(1L);
            rental1.setRentedAt(LocalDateTime.now().minusDays(2));
            rental1.setReturnedAt(null); // still out
            rental1.setStatus("RENTED");
            rentalRepo.save(rental1);

            // 🛠 Repairs (shop board + user board)
            Repair repair1 = new Repair();
            repair1.setSurfboardId(shopBoard2.getId());
            repair1.setIssue("Fin broken during rental");
            repair1.setStatus("CREATED");
            repairRepo.save(repair1);

            Repair repair2 = new Repair();
            repair2.setSurfboardId(userBoard1.getId());
            repair2.setIssue("Crack on bottom - user-owned");
            repair2.setStatus("IN_PROGRESS");
            repairRepo.save(repair2);

            System.out.println("🌱 Seed data loaded: surfboards, rentals, repairs");
        }
    }
}
