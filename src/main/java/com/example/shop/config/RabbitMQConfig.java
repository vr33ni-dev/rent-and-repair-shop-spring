package com.example.shop.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

     @Bean
    public DirectExchange surfboardExchange() {
        return new DirectExchange("surfboard.exchange", true, false); // Durable, not auto-deleted
    }

    @Bean
    public Queue repairQueue() {
        return new Queue("repair.queue", true); // Durable queue
    }

     @Bean
    public Queue rentalQueue() {
        return new Queue("rental.queue", true); // Durable queue
    }

     @Bean
    public Binding repairQueueBinding(Queue repairQueue, DirectExchange surfboardExchange) {
        return BindingBuilder.bind(repairQueue).to(surfboardExchange).with("repair.created");
    }

     @Bean
    public Binding rentalQueueBinding(Queue rentalQueue, DirectExchange surfboardExchange) {
        return BindingBuilder.bind(rentalQueue).to(surfboardExchange).with("rental.requested");
    }

     @Bean
    public Binding repairCompletedQueueBinding(Queue repairQueue, DirectExchange surfboardExchange) {
        return BindingBuilder.bind(repairQueue).to(surfboardExchange).with("repair.completed");
    }

    @Bean
    public Binding rentalCompletedQueueBinding(Queue rentalQueue, DirectExchange surfboardExchange) {
        return BindingBuilder.bind(rentalQueue).to(surfboardExchange).with("rental.completed");
    }
}