package com.example.shop.service;

import com.example.shop.dto.RentalMessage;
import com.example.shop.dto.RepairMessage;
import com.example.shop.dto.RentalRequest;
import com.example.shop.model.Rental;
import com.example.shop.model.Surfboard;
import com.example.shop.repository.RentalRepository;
import com.example.shop.repository.RepairRepository;
import com.example.shop.repository.SurfboardRepository;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final SurfboardRepository surfboardRepository;
    private final RepairRepository repairRepository;

    private final RabbitTemplate rabbitTemplate;

    public RentalService(RentalRepository rentalRepository, SurfboardRepository surfboardRepository,
            RepairRepository repairRepository,
            RabbitTemplate rabbitTemplate) {
        this.rentalRepository = rentalRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.surfboardRepository = surfboardRepository;
        this.repairRepository = repairRepository;
    }

    public void rentBoard(RentalRequest request) {
        Surfboard board = surfboardRepository.findById(request.getSurfboardId())
                .orElseThrow(() -> new RuntimeException("Surfboard not found with ID: " + request.getSurfboardId()));

        if (board.isAvailableForRental()) {
            Rental rental = new Rental();
            rental.setUserId(request.getUserId());
            rental.setSurfboardId(request.getSurfboardId());
            rental.setRentedAt(LocalDateTime.now());
            rental.setStatus("CREATED");
            Rental savedRental = rentalRepository.save(rental);

            rabbitTemplate.convertAndSend("surfboard.exchange", "rental.created", new RentalMessage(
                    savedRental.getId(),
                    savedRental.getSurfboardId(),
                    savedRental.getUserId(),
                    false // not damaged at this point
            ));
            System.out.println("✅ Rental created for surfboard ID: " + board.getId());
        }
        else {
            System.out.println("⚠️ Surfboard not available for rental: " + board.getId());
        }   
    }

    public void returnBoard(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental not found with ID: " + rentalId));
        rental.setReturnedAt(LocalDateTime.now());
        rental.setStatus("RETURNED");
        Rental savedRental = rentalRepository.save(rental);

        Surfboard board = surfboardRepository.findById(rental.getSurfboardId())
                .orElseThrow(
                        () -> new IllegalStateException("Surfboard not found with ID: " + rental.getSurfboardId()));

        // Only apply damage if board is not already damaged and no open repair exists
        boolean isDamaged = new Random().nextBoolean();
        if (isDamaged && !board.isDamaged()) {
            board.setDamaged(true);
            board.setAvailable(false); // Mark as not available for rental
            surfboardRepository.save(board);

            // Send repair message
            RepairMessage repairMsg = new RepairMessage(board.getId(), "Ding on the tail", board.getOwnerUserId());
            rabbitTemplate.convertAndSend("surfboard.exchange", "repair.created", repairMsg);
            System.out.println("🛠️ Sent repair.created for board ID: " + board.getId());
        } else {
            // No damage or already in repair
            if (board.isDamaged()) {
                System.out.println("⚠️ Skipped marking as damaged: already marked or repair exists.");
            } else {
                surfboardRepository.save(board);
            }
        }

        // Emit rental.completed message
        RentalMessage rentalMessage = new RentalMessage(savedRental.getId(), board.getId(),
                savedRental.getUserId(), isDamaged);
        rabbitTemplate.convertAndSend("surfboard.exchange", "rental.completed", rentalMessage);

        // Randomly decide if a repair is needed
        if (isDamaged) {
            RepairMessage repairMsg = new RepairMessage(board.getId(), "Ding on the tail", board.getOwnerUserId());
            rabbitTemplate.convertAndSend("surfboard.exchange", "repair.created", repairMsg);
        }
        System.out.println("✅ Rental completed for rental ID: " + rentalId);
    }

    @RabbitListener(queues = "rental.completed.queue")
    public void listenCompleted(RentalMessage msg) {
        System.out.println("🎧 Received rental.completed: " + msg.getRentalId());
    }

}
