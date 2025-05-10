package com.example.shop.service;

import com.example.shop.model.Surfboard;
import com.example.shop.repository.SurfboardRepository;
import com.example.shop.dto.RentalMessage;
import com.example.shop.dto.RepairMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final SurfboardRepository surfboardRepository;

    public InventoryService(SurfboardRepository surfboardRepository) {
        this.surfboardRepository = surfboardRepository;
    }

    @RabbitListener(queues = "rental.completed.queue")
    public void handleRentalReturned(RentalMessage message) {
        Surfboard board = surfboardRepository.findById(message.getSurfboardId())
                .orElseThrow(() -> new IllegalArgumentException("Surfboard not found"));

        board.setAvailable(true);
        if (message.isDamaged()) {
            board.setDamaged(true);
        }
        surfboardRepository.save(board);

        System.out.println("✅ Inventory updated after rental return for board ID: " + board.getId());
    }

    @RabbitListener(queues = "repair.completed.queue")
    public void handleRepairCompleted(RepairMessage message) {
        if (message.getSurfboardId() == null) {
            System.out.println("⚠️ Repair completed for user-owned board (not in inventory): " + message.getCustomerId());
            return;
        }
        // Fetch the surfboard from the repository
        Surfboard board = surfboardRepository.findById(message.getSurfboardId())
                .orElseThrow(() -> new IllegalArgumentException("Surfboard not found"));

        // Only update availability for shop-owned boards
        if (board.isShopOwned() == true) {
            board.setDamaged(false);
            board.setAvailable(true);
            surfboardRepository.save(board);
            System.out.println("🛠️ Repair completed, board available: " + board.getId());
        } else {
            // User-owned board: only log, no persistence
            System.out
                    .println("🛠️ Repair completed for user-owned board (not updated in inventory): " + board.getId());
        }
    }
}
