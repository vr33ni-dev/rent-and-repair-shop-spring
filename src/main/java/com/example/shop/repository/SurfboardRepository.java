package com.example.shop.repository;

import com.example.shop.model.Surfboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurfboardRepository extends JpaRepository<Surfboard, Long> {

    List<Surfboard> findByShopOwnedTrue(); // All rentable boards

    List<Surfboard> findByOwnerUserId(Long userId); // Boards brought in by customers

    List<Surfboard> findByDamagedTrue(); // Boards needing or under repair
}
