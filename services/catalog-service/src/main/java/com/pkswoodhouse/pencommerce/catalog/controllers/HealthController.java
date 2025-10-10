package com.pkswoodhouse.pencommerce.catalog.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {
    @GetMapping("/health")
    public Map<String, Object> health() {
        var response = new HashMap();

        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now());
        response.put("service", "RealtiMate AI Backend");
        response.put("version", "0.0.1-SNAPSHOT");
        response.put("profile", System.getProperty("spring.profiles.active", "default"));

        return response;
    }

    @GetMapping("/")
    public Map<String, String> root() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "PenCommerce Catalog Service API");
        response.put("status", "Running");
        response.put("documentation", "/swagger-ui/index.html");
        return response;
    }
}
