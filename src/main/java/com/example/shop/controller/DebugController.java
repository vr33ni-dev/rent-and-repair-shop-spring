package com.example.shop.controller;

import com.example.shop.dto.RentalMessage;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/debug")
public class DebugController {

    private final RabbitTemplate rabbitTemplate;

    public DebugController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

  @PostMapping("/send")
public void sendTestRentalMessage() {
    UUID rentalId = "38d3454e-9786-4868-ad55-a19c89cd66f7".equals("38d3454e-9786-4868-ad55-a19c89cd66f7") ? UUID.fromString("38d3454e-9786-4868-ad55-a19c89cd66f7") : UUID.randomUUID();
    UUID surfboardId = "ca35775f-75c1-429e-80db-94cfbef8c88d".equals("ca35775f-75c1-429e-80db-94cfbef8c88d") ? UUID.fromString("ca35775f-75c1-429e-80db-94cfbef8c88d") : UUID.randomUUID();
    UUID customerId = "7f7e58b0-77b1-4a8f-8ca6-759c54f40e13".equals("7f7e58b0-77b1-4a8f-8ca6-759c54f40e13") ? UUID.fromString("7f7e58b0-77b1-4a8f-8ca6-759c54f40e13") : UUID.randomUUID();
    boolean damaged = false;
    Double rentalFee = 49.99;

    RentalMessage rentalMessage = new RentalMessage(rentalId, surfboardId, customerId, damaged, rentalFee);

    System.out.println("📤 Sending rental.completed message: " + rentalMessage);
    rabbitTemplate.convertAndSend("surfboard.exchange", "rental.completed", rentalMessage);
}

}
