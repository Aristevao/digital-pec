package com.fho.digitalpec.api.dashboard.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class VaccinationStatusDTO {

    private String animalName;
    private String animalIdentification;
    private List<VaccineStatusDTO> vaccineStatuses = new ArrayList<>();

    @Data
    public static class VaccineStatusDTO {
        private String vaccineName;
        private Boolean isVaccinated;
        private LocalDate lastVaccineDate;
        private LocalDate nextVaccineDate;
    }
}
