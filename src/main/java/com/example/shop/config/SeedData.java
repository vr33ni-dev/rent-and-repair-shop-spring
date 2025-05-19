package com.example.shop.config;

import com.example.shop.model.Surfboard;
import com.example.shop.enums.RentalStatus;
import com.example.shop.enums.RepairStatus;
import com.example.shop.model.Customer;
import com.example.shop.model.Rental;
import com.example.shop.model.Repair;
import com.example.shop.repository.SurfboardRepository;
import com.example.shop.repository.CustomerRepository;
import com.example.shop.repository.RentalRepository;
import com.example.shop.repository.RepairRepository;

 
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SeedData implements ApplicationListener<ContextRefreshedEvent> {

    private final SurfboardRepository surfboardRepo;
    private final RentalRepository rentalRepo;
    private final RepairRepository repairRepo;
    private final CustomerRepository customerRepo;

    private boolean alreadySeeded = false;

    public SeedData(
            SurfboardRepository surfboardRepo,
            RentalRepository rentalRepo,
            RepairRepository repairRepo,
            CustomerRepository customerRepo) {
        this.surfboardRepo = surfboardRepo;
        this.rentalRepo = rentalRepo;
        this.repairRepo = repairRepo;
        this.customerRepo = customerRepo;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySeeded) return;

        if (surfboardRepo.count() == 0) {
            // Shop-owned surfboards
            Surfboard shopBoard1 = new Surfboard("Longboard Classic", true, false, true, null, "/images/longboard.jpg");
            Surfboard shopBoard2 = new Surfboard("Shortboard Pro", false, true, true, null, "/images/shortboard.jpg");
            Surfboard shopBoard3 = new Surfboard("Funboard XL", false, false, true, null, "/images/funboard.jpg");
    
            shopBoard1 = surfboardRepo.save(shopBoard1);
            shopBoard2 = surfboardRepo.save(shopBoard2);
            shopBoard3 = surfboardRepo.save(shopBoard3);
             
            // User-owned boards (not available for rental)
            Customer customer1 = customerRepo.save(new Customer("Alice", "alice@example.com"));
            Customer customer2 = customerRepo.save(new Customer("Bob", "bob@example.com"));
            Surfboard userBoard1 = new Surfboard("Vintage Malibu", false, true, false, customer1.getId(), "/images/userboard1.jpg");
            Surfboard userBoard2 = new Surfboard("Personal MiniMal", false, true, false, customer2.getId(), "/images/userboard2.jpg");
            
            userBoard1 = surfboardRepo.save(userBoard1);
            userBoard2 = surfboardRepo.save(userBoard2);

            // Rentals
            Rental rental1 = new Rental();
            Customer customer3 = customerRepo.save(new Customer("Kook1", "kook@example.com"));

            rental1.setSurfboardId(shopBoard3.getId());
            rental1.setCustomerId(customer3.getId());
            rental1.setRentalFee(15.0);
            rental1.setRentedAt(LocalDateTime.now().minusDays(1));
            rental1.setStatus(RentalStatus.CREATED);
            rentalRepo.save(rental1);
    
            // Repairs
            Repair repair1 = new Repair();
            Customer customer4 = customerRepo.save(new Customer("Kook2", "kook@example.com"));
            repair1.setSurfboardId(shopBoard2.getId());
            repair1.setCustomerId(customer4.getId());
            repair1.setIssue("Cracked fin");
            repair1.setStatus(RepairStatus.CREATED);
            repair1.setCreatedAt(LocalDateTime.now().minusDays(1)); // required
            repair1.setRepairFee(20.0);
            repairRepo.save(repair1);
    
            Repair repair2 = new Repair();
            repair2.setSurfboardId(userBoard1.getId()); // it's not in shop inventory
            repair2.setCustomerId(customer1.getId());
            repair2.setIssue("Tail damage");
            repair2.setStatus(RepairStatus.CREATED);
            repair2.setCreatedAt(LocalDateTime.now().minusDays(1)); // required
            repair2.setRepairFee(15.0);
            repairRepo.save(repair2);
    
            Repair repair3 = new Repair();
            repair3.setSurfboardId(userBoard2.getId()); // it's not in shop inventory
            repair3.setCustomerId(customer2.getId());
            repair3.setIssue("Tail damage");
            repair3.setStatus(RepairStatus.CREATED);
            repair3.setCreatedAt(LocalDateTime.now().minusDays(1)); // required
            repair3.setRepairFee(15.0);
            repairRepo.save(repair3);
    
            System.out.println("🌱 Seed data loaded successfully");
        }
    


        alreadySeeded = true;
    }
}
 