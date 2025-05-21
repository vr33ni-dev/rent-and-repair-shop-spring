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
            // --- shop-owned boards ---
            Surfboard s1 = new Surfboard();
            s1.setName("Longboard Classic");
            s1.setDescription("");
            s1.setSizeText("9\"6");
            s1.setSize(parseInches("9\"6"));
            s1.setShopOwned(true);
            s1.setDamaged(false);
            s1.setAvailable(true);
            s1.setImageUrl("/images/longboard.jpg");
            surfboardRepo.save(s1);

            Surfboard s2 = new Surfboard();
            s2.setName("Shortboard Pro");
            s2.setDescription("");
            s2.setSizeText("5\"10");
            s2.setSize(parseInches("5\"10"));
            s2.setShopOwned(true);
            s2.setDamaged(true);
            s2.setAvailable(false);
            s2.setImageUrl("/images/shortboard.jpg");
            surfboardRepo.save(s2);

            Surfboard s3 = new Surfboard();
            s3.setName("Funboard XL");
            s3.setDescription("");
            s3.setSizeText("6\"6");
            s3.setSize(parseInches("6\"6"));
            s3.setShopOwned(true);
            s3.setDamaged(false);
            s3.setAvailable(false);
            s3.setImageUrl("/images/funboard.jpg");
            surfboardRepo.save(s3);

            // --- user-owned boards ---
            Customer c1 = customerRepo.save(new Customer("Alice", "alice@example.com"));
            Surfboard u1 = new Surfboard();
            u1.setName("Vintage Malibu");
            u1.setDescription("");
            u1.setSizeText("");          // blank → size == null
            u1.setSize(null);
            u1.setShopOwned(false);
            u1.setDamaged(true);
            u1.setAvailable(false);
            u1.setOwnerId(c1.getId());
            u1.setImageUrl("/images/userboard1.jpg");
            surfboardRepo.save(u1);

            Customer c2 = customerRepo.save(new Customer("Bob", "bob@example.com"));
            Surfboard u2 = new Surfboard();
            u2.setName("Personal MiniMal");
            u2.setDescription("");
            u2.setSizeText("6\"6");
            u2.setSize(parseInches("6\"6"));
            u2.setShopOwned(false);
            u2.setDamaged(true);
            u2.setAvailable(false);
            u2.setOwnerId(c2.getId());
            u2.setImageUrl("/images/userboard2.jpg");
            surfboardRepo.save(u2);

            // --- one rental ---
            Customer c3 = customerRepo.save(new Customer("Kook1", "kook@example.com"));
            Rental r1 = new Rental();
            r1.setSurfboardId(s3.getId());
            r1.setCustomerId(c3.getId());
            r1.setRentalFee(15.0);
            r1.setRentedAt(LocalDateTime.now().minusDays(1));
            r1.setStatus(RentalStatus.CREATED);
            rentalRepo.save(r1);

            // --- some repairs ---
            Customer c4 = customerRepo.save(new Customer("Kook2", "kook2@example.com"));
            Repair rep1 = new Repair();
            rep1.setSurfboardId(s2.getId());
            rep1.setCustomerId(c4.getId());
            rep1.setIssue("Cracked fin");
            rep1.setRepairFee(20.0);
            rep1.setStatus(RepairStatus.CREATED);
            rep1.setCreatedAt(LocalDateTime.now().minusDays(1));
            repairRepo.save(rep1);

            // …etc…

            System.out.println("🌱 Seed data loaded");
        }

        alreadySeeded = true;
    }

/**
 * Parse a feet-inches string like "5\"10" or "5\"2" into a Double
 * whose decimal part is literally the inches.  
 * Returns null on null/blank or on any parse failure.
 */
private Double parseInches(String txt) {
    if (txt == null || txt.isBlank()) return null;
    // split on the quote:
    String[] parts = txt.split("\"", 2);
    if (parts.length != 2) return null;

    String feetStr  = parts[0].trim();
    String inchStr  = parts[1].trim();

    // guard against empties:
    if (feetStr.isEmpty() || inchStr.isEmpty()) return null;

    try {
        // parse each piece as an integer:
        int feet  = Integer.parseInt(feetStr);
        int inch  = Integer.parseInt(inchStr);
        // build the feet.inch string, preserving leading zeros in inch if any:
        String decimalString = feet + "." + inch;
        return Double.parseDouble(decimalString);
    } catch (NumberFormatException ex) {
        return null;
    }
}

}
 