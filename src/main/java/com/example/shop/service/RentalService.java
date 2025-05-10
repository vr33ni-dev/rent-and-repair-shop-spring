package com.example.shop.service;

import com.example.shop.dto.RentalMessage;
import com.example.shop.dto.RepairMessage;
import com.example.shop.dto.RentalRequest;
import com.example.shop.model.Rental;
import com.example.shop.repository.RentalRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class RentalService {

    private final RentalRepository rentalRepo;
    private final RabbitTemplate rabbitTemplate;

    public RentalService(RentalRepository rentalRepo, RabbitTemplate rabbitTemplate) {
        this.rentalRepo = rentalRepo;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void rentBoard(RentalRequest request) {
        Rental rental = new Rental();
        rental.setUserId(request.getUserId());
        rental.setSurfboardId(request.getSurfboardId());
        rental.setRentedAt(LocalDateTime.now());
        rental.setStatus("REQUESTED");
        rentalRepo.save(rental);

        rabbitTemplate.convertAndSend("surfboard.exchange", "rental.requested", new RentalMessage(rental));
    }

    public void returnBoard(Long rentalId) {
        Rental rental = rentalRepo.findById(rentalId).orElseThrow();
        rental.setReturnedAt(LocalDateTime.now());
        rental.setStatus("RETURNED");
        rentalRepo.save(rental);
    
        // Emit rental.completed message
        RentalMessage rentalMessage = new RentalMessage(rental);
        rabbitTemplate.convertAndSend("surfboard.exchange", "rental.completed", rentalMessage);
    
        // Randomly decide if a repair is needed
        if (new Random().nextBoolean()) {
            RepairMessage repairMessage = new RepairMessage(rental.getSurfboardId(), "Ding on the tail");
            rabbitTemplate.convertAndSend("surfboard.exchange", "repair.created", repairMessage);
        }
    
        System.out.println("✅ Rental completed for rental ID: " + rentalId);
    }
}
