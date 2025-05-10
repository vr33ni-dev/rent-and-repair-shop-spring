package com.example.shop.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
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
    public Queue repairCompletedQueue() {
        return new Queue("repair.completed.queue", true);
    }

    @Bean
    public Queue rentalCompletedQueue() {
        return new Queue("rental.completed.queue", true);
    }

    // === Bindings ===
    @Bean
    public Binding repairQueueBinding(Queue repairQueue, DirectExchange surfboardExchange) {
        return BindingBuilder.bind(repairQueue).to(surfboardExchange).with("repair.created");
    }

    @Bean
    public Binding repairCompletedBinding(Queue repairCompletedQueue, DirectExchange surfboardExchange) {
        return BindingBuilder.bind(repairCompletedQueue).to(surfboardExchange).with("repair.completed");
    }

    @Bean
    public Binding rentalQueueBinding(Queue rentalQueue, DirectExchange surfboardExchange) {
        return BindingBuilder.bind(rentalQueue).to(surfboardExchange).with("rental.created");
    }

    @Bean
    public Binding rentalCompletedBinding(Queue rentalCompletedQueue, DirectExchange surfboardExchange) {
        return BindingBuilder.bind(rentalCompletedQueue).to(surfboardExchange).with("rental.completed");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        return template;
    }
}