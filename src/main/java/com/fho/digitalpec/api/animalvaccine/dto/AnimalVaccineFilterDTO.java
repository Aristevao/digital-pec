package com.fho.digitalpec.api.animalvaccine.dto;

import java.time.LocalDate;
import java.util.List;

import com.fho.digitalpec.api.animal.entity.Animal;
import com.fho.digitalpec.api.animalvaccine.entity.NextApplicationDate;
import com.fho.digitalpec.api.vaccine.entity.Vaccine;

import lombok.Data;

@Data
public class AnimalVaccineFilterDTO {

    private Animal animal;
    private Vaccine vaccine;
    private Boolean completed;
    private LocalDate applicationDate;
    private List<NextApplicationDate> nextApplicationDates;
}
