package com.example.shop.service;

import com.example.shop.dto.RepairMessage;
import com.example.shop.dto.RepairRequest;
import com.example.shop.model.Repair;
import com.example.shop.model.Surfboard;
import com.example.shop.repository.RepairRepository;
import com.example.shop.repository.SurfboardRepository;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RepairService {

    private final RepairRepository repairRepository;
    private final SurfboardRepository surfboardRepository;
    private final RabbitTemplate rabbitTemplate;

    public RepairService(RepairRepository repairRepository, SurfboardRepository surfboardRepository,
            RabbitTemplate rabbitTemplate) {
        this.repairRepository = repairRepository;
        this.surfboardRepository = surfboardRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void createRepair(RepairRequest request) {
        Repair repair = new Repair();
        repair.setSurfboardId(request.getSurfboardId());
        repair.setIssue(request.getIssue());
        repair.setStatus("CREATED");
        repairRepository.save(repair);
        System.out.println("Manual repair created with ID: " + repair.getId());
    }

    public void markRepairAsCompleted(Long repairId) {
        Repair repair = repairRepository.findById(repairId)
                .orElseThrow(() -> new IllegalArgumentException("Repair not found with ID: " + repairId));

        // Update repair status
        repair.setStatus("COMPLETED");
        repairRepository.save(repair);

        // Update surfboard status
        Surfboard board = surfboardRepository.findById(repair.getSurfboardId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Surfboard not found with ID: " + repair.getSurfboardId()));
        board.setDamaged(false);
        board.setAvailable(true); // optional: only if shop-owned and ready for rental
        surfboardRepository.save(board);

        // Emit repair.completed message (optional, but useful for
        // billing/notifications)
        RepairMessage message = new RepairMessage(repair.getSurfboardId(), repair.getIssue());
        rabbitTemplate.convertAndSend("surfboard.exchange", "repair.completed", message);

        System.out.println("Repair completed and board updated for board ID: " + board.getId());
    }

    public void cancelRepair(Long repairId) {
        Repair repair = repairRepository.findById(repairId)
                .orElseThrow(() -> new IllegalArgumentException("Repair not found with ID: " + repairId));

        if ("COMPLETED".equalsIgnoreCase(repair.getStatus())) {
            throw new IllegalStateException("Cannot cancel a completed repair.");
        }

        repair.setStatus("CANCELED");
        repairRepository.save(repair);

        System.out.println("Repair canceled: ID " + repairId);
    }

    @RabbitListener(queues = "repair.queue")
    public void processRepair(RepairMessage message) {
        System.out.println("Repair requested for board ID: " + message.getSurfboardId());
        Repair repair = new Repair();
        repair.setSurfboardId(message.getSurfboardId());
        repair.setIssue(message.getIssue());
        repair.setStatus("CREATED");
        repairRepository.save(repair);
        System.out.println("Automatic repair created with ID: " + repair.getId());
    }

}
