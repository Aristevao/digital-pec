package com.fho.digitalpec.api.animalvaccine.dto;

import java.time.LocalDate;
import java.util.List;

import com.fho.digitalpec.api.animal.entity.Animal;
import com.fho.digitalpec.api.animalvaccine.entity.NextApplicationDate;
import com.fho.digitalpec.api.vaccine.entity.Vaccine;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AnimalVaccineDTO {

    private Long id;

    @NotNull
    private Animal animal;

    @NotNull
    private Vaccine vaccine;

    private Boolean completed;

    @NotNull
    private LocalDate applicationDate;

    private List<NextApplicationDate> nextApplicationDates;
}
