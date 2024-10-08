package com.fho.digitalpec.api.animal.dto;

import java.time.LocalDate;

import com.fho.digitalpec.api.animal.entity.Sex;
import com.fho.digitalpec.api.unit.entity.Unit;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class AnimalDTO {

    private Long id;

    @Size(max = 80)
    private String name;

    @NotNull
    @Size(max = 80)
    private String identification;

    @Size(max = 80)
    private String specie;

    @Size(max = 80)
    private String breed;

    private Sex sex;

    private LocalDate birthdate;

    @PastOrPresent
    private LocalDate registrationDate;

    @Size(max = 500)
    private String description;

    @NotNull
    private Unit unit;

    private String picture;
}
