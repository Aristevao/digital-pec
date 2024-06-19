package com.fho.digitalpec.api.animalvaccine.dto;

import java.time.LocalDate;
import java.util.List;

import com.fho.digitalpec.utils.mapper.SimpleDTO;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AnimalVaccineDTO {

    private Long id;

    @NotNull
    private SimpleDTO animal;

    @NotNull
    private SimpleDTO vaccine;

    private Boolean completed;

    private LocalDate applicationDate;

    private List<LocalDate> nextApplicationDates;
}
