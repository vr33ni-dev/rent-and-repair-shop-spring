package com.example.shop.tasks;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Profile("keepalive")
@Component
public class SelfPingTask {

    private static final List<String> PING_URLS = List.of(
        "https://self-ping-task.onrender.com/actuator/health",          // Pings *itself*
        "https://rent-and-repair-shop-spring.onrender.com/actuator/health"  // Pings *main app*
    );

    @Scheduled(fixedDelay = 4 * 60 * 1000) // Every 4 minutes
    public void ping() {
        for (String url : PING_URLS) {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(3000);
                connection.setReadTimeout(3000);
                int responseCode = connection.getResponseCode();
                System.out.println("✅ Ping to " + url + " responded with: " + responseCode);
            } catch (Exception e) {
                System.err.println("❌ Ping to " + url + " failed: " + e.getMessage());
            }
        }
    }
}
