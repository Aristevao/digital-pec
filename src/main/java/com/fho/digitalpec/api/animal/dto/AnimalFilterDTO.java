package com.fho.digitalpec.api.animal.dto;

import java.time.LocalDate;
import java.util.List;

import com.fho.digitalpec.api.animal.entity.Sex;
import com.fho.digitalpec.api.user.entity.User;

import lombok.Data;

@Data
public class AnimalFilterDTO {

    private String name;
    private String identification;
    private String breed;
    private Sex sex;
    private LocalDate birthdate; // TODO: Add "birth month" or "birth year"
    private LocalDate registrationDate; // TODO: Add with "birth month" or "birth year"
    private String description;
    private User owner;
    private List<Long> vaccinationIds;
}
