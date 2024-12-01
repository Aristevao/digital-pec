package com.fho.digitalpec.api.dashboard.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fho.digitalpec.api.animal.service.AnimalService;
import com.fho.digitalpec.api.dashboard.dto.AnimalGrowthDTO;
import com.fho.digitalpec.api.dashboard.dto.VaccinationStatusDTO;
import com.fho.digitalpec.api.dashboard.service.VaccinationStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("dashboard")
public class DashboardController {

    private final VaccinationStatusService vaccinationStatusService;
    private final AnimalService animalService;

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

    @GetMapping("vaccination-status")
    public ResponseEntity<List<VaccinationStatusDTO>> getVaccinationStatus() {
        List<VaccinationStatusDTO> status = vaccinationStatusService.getVaccinationStatus();
        return ResponseEntity.ok(status);
    }

    @GetMapping("animal-growth")
    public List<AnimalGrowthDTO> getAnimalGrowth(@RequestParam("startDate") String startDate,
                                                 @RequestParam("endDate") String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        return animalService.getAnimalGrowthByPeriod(start, end);
    }
}
