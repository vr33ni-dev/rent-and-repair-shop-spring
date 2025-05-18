package com.example.shop.listener;

 
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.shop.dto.RentalMessage;


@Component
public class DebugListener {

// @RabbitListener(queues = "rental.completed.queue")
// public void handleRentalCompleted(RentalMessage msg) {
//     try {
//         System.out.println("📥 RECEIVED rental.completed: " + msg);
//     } catch (Exception e) {
//         System.err.println("❌ Error in listener: " + e.getMessage());
//         e.printStackTrace();
//     }
// }

}
