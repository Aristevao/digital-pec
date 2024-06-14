package com.fho.digitalpec.api.animal.dto;

import java.time.LocalDate;

import com.fho.digitalpec.api.animal.entity.Sex;

import lombok.Data;

@Data
public class AnimalFilterDTO {

    private String name;
    private String identification;
    private String breed;
    private Sex sex;
    private LocalDate birthdate;
    private LocalDate registrationDate;
    private String description;
}
