package com.example.shop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.Metamodel;

@SpringBootApplication
@EnableScheduling
public class RentAndAndRepairShopApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(RentAndAndRepairShopApplication.class);
        Environment env = app.run(args).getEnvironment();

        System.out.println("🧪 Loaded config:");
        System.out.println("🔍 JDBC URL: " + env.getProperty("spring.datasource.url"));
    }

    @Profile("dev")
    @Bean
    public CommandLineRunner init(EntityManager entityManager) {
        return args -> {
            Metamodel metamodel = entityManager.getMetamodel();
            System.out.println("Entities detected by JPA:");
            metamodel.getEntities().forEach(e -> System.out.println(" - " + e.getName()));
        };
    }

}
