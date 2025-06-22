package com.example.shop.tasks;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;

@Profile("keepalive")
@Component
public class SelfPingTask {

private static final String PING_URL = "https://rent-and-repair-shop-spring.onrender.com/actuator/health";

    @Scheduled(fixedDelay = 4 * 60 * 1000) // Every 4 minutes
    public void ping() {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(PING_URL).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            int responseCode = connection.getResponseCode();
            System.out.println("✅ Self-ping response code: " + responseCode);
        } catch (Exception e) {
            System.err.println("❌ Self-ping failed: " + e.getMessage());
        }
    }
}
