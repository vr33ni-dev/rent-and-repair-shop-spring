package com.example.shop;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableRabbit
public class RentAndAndRepairShopApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(RentAndAndRepairShopApplication.class);
        Environment env = app.run(args).getEnvironment();
        
        System.out.println("🧪 Loaded config:");
        System.out.println("🔍 JDBC URL: " + env.getProperty("spring.datasource.url"));
    }
    

}
