package com.fho.digitalpec.api.animalvaccine.dto;

import java.time.LocalDate;
import java.util.List;

import com.fho.digitalpec.utils.mapper.SimpleDTO;

import lombok.Data;

@Data
public class AnimalVaccineDTO {

    private Long id;

    private SimpleDTO animal;

    private SimpleDTO vaccine;

    private Boolean completed;

    private LocalDate applicationDate;

    private List<LocalDate> nextApplicationDates;
}
