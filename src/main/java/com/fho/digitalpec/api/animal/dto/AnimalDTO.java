package com.fho.digitalpec.api.animal.dto;

import java.time.LocalDate;

import com.fho.digitalpec.api.animal.entity.Sex;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class AnimalDTO {

    private Long id;

    @NotNull
    @Size(max = 80)
    private String name;

    @Size(max = 50)
    private String identification;

    @Size(max = 80)
    private String specie;

    @Size(max = 80)
    private String breed;

    @NotNull
    private Sex sex;

    @NotNull
    private LocalDate birthdate;

    @NotNull
    @PastOrPresent
    private LocalDate registrationDate;

    @Size(max = 1000)
    private String description;
}
