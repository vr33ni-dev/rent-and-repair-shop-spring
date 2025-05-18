package com.example.shop.repository;

import com.example.shop.model.Surfboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SurfboardRepository extends JpaRepository<Surfboard, UUID> {

    List<Surfboard> findByShopOwnedTrue(); // All rentable boards
    List<Surfboard> findByAvailableTrue(); // All available boards
    List<Surfboard> findByAvailableTrueAndShopOwnedTrue(); // Rentable and available boards
    List<Surfboard> findByDamagedTrue(); // Boards needing or under repair
}
