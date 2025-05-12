package com.example.shop.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
  public static final String EXCHANGE = "surfboard.exchange";
  public static final String RENTAL_COMPLETED_QUEUE = "rental.completed.queue";
  public static final String REPAIR_COMPLETED_QUEUE = "repair.completed.queue";
  public static final String REPAIR_QUEUE = "repair.queue";

  @Bean
  TopicExchange surfboardExchange() {
    return new TopicExchange(EXCHANGE);
  }

  // Declare the repair.request queue
  @Bean
  public Queue repairQueue() {
    return new Queue(REPAIR_QUEUE, true);
  }

  // Bind repair.queue to surfboard.exchange on "repair.created"
  @Bean
  public Binding repairRequestBinding(TopicExchange surfboardExchange, Queue repairQueue) {
    return BindingBuilder
        .bind(repairQueue)
        .to(surfboardExchange)
        .with("repair.created");
  }

  @Bean
  Queue rentalCompletedQueue() {
    return new Queue(RENTAL_COMPLETED_QUEUE, true);
  }

  @Bean
  Queue repairCompletedQueue() {
    return new Queue(REPAIR_COMPLETED_QUEUE, true);
  }

  @Bean
  Binding rentalCompletedBinding(TopicExchange surfboardExchange, Queue rentalCompletedQueue) {
    return BindingBuilder
        .bind(rentalCompletedQueue)
        .to(surfboardExchange)
        .with("rental.completed");
  }

  @Bean
  Binding repairCompletedBinding(TopicExchange surfboardExchange, Queue repairCompletedQueue) {
    return BindingBuilder
        .bind(repairCompletedQueue)
        .to(surfboardExchange)
        .with("repair.completed");
  }

  // —————— JSON CONVERTER ——————

  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public RabbitTemplate rabbitTemplate(
      ConnectionFactory cf,
      MessageConverter jsonMessageConverter) {
    RabbitTemplate tpl = new RabbitTemplate(cf);
    tpl.setMessageConverter(jsonMessageConverter);
    return tpl;
  }

  @Bean
  public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
      ConnectionFactory cf,
      MessageConverter jsonMessageConverter) {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(cf);
    factory.setMessageConverter(jsonMessageConverter);
    return factory;
  }
}
