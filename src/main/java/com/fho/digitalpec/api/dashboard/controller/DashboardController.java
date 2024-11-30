package com.fho.digitalpec.api.dashboard.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @GetMapping("/data")
    public ResponseEntity<Map<String, Object>> getDashboardData() {
        Map<String, Object> data = new HashMap<>();
        data.put("animals", 53);
        data.put("farms", 2);
        data.put("cowsInCorral", 10);

        Map<String, Integer> categories = new HashMap<>();
        categories.put("Boi", 20);
        categories.put("Vaca", 15);
        categories.put("Bezerro", 18);
        data.put("animalCategories", categories);

        return ResponseEntity.ok(data);
    }

    @GetMapping("/weather")
    public ResponseEntity<Map<String, String>> getWeatherData() {
        Map<String, String> weather = new HashMap<>();
        weather.put("description", "Ensolarado");
        weather.put("temperature", "28");
        return ResponseEntity.ok(weather);
    }
}
