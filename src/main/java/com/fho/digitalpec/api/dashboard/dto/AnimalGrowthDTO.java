package com.fho.digitalpec.api.dashboard.dto;

import java.time.LocalDate;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalGrowthDTO {
    private LocalDate date;
    private Map<String, Long> speciesCount;
}