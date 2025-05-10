package com.example.shop.service;

import com.example.shop.dto.RentalMessage;
import com.example.shop.dto.RepairMessage;
import com.example.shop.dto.RentalRequest;
import com.example.shop.model.Rental;
import com.example.shop.model.Surfboard;
import com.example.shop.repository.RentalRepository;
import com.example.shop.repository.SurfboardRepository;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final SurfboardRepository surfboardRepository;
    
    private final RabbitTemplate rabbitTemplate;

    public RentalService(RentalRepository rentalRepository, SurfboardRepository surfboardRepository, RabbitTemplate rabbitTemplate) {
        this.rentalRepository = rentalRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.surfboardRepository = surfboardRepository;
    }

    public void rentBoard(RentalRequest request) {
        Rental rental = new Rental();
        rental.setUserId(request.getUserId());
        rental.setSurfboardId(request.getSurfboardId());
        rental.setRentedAt(LocalDateTime.now());
        rental.setStatus("REQUESTED");
        Rental savedRental = rentalRepository.save(rental);

        rabbitTemplate.convertAndSend("surfboard.exchange", "rental.requested", new RentalMessage(
                savedRental.getId(),
                savedRental.getSurfboardId(),
                savedRental.getUserId(),
                false // not damaged at this point
        ));
    }

    public void returnBoard(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId).orElseThrow();
        rental.setReturnedAt(LocalDateTime.now());
        rental.setStatus("RETURNED");
        Rental savedRental = rentalRepository.save(rental);

        Surfboard board = surfboardRepository.findById(rental.getSurfboardId())
                .orElseThrow();

        boolean isDamaged = new Random().nextBoolean();
        board.setDamaged(isDamaged);
        board.setAvailable(!isDamaged);
        Surfboard savedBoard = surfboardRepository.save(board);

        // Emit rental.completed message
        RentalMessage rentalMessage = new RentalMessage(savedRental.getId(), savedBoard.getId(), savedRental.getUserId(), isDamaged);
        rabbitTemplate.convertAndSend("surfboard.exchange", "rental.completed", rentalMessage);

        // Randomly decide if a repair is needed
        if (isDamaged) {
            RepairMessage repairMsg = new RepairMessage(savedBoard.getId(), "Ding on the tail");
            rabbitTemplate.convertAndSend("surfboard.exchange", "repair.created", repairMsg);
        }
        System.out.println("✅ Rental completed for rental ID: " + rentalId);
    }
}
