package com.fho.digitalpec.api.vaccine.dto;

import lombok.Data;

@Data
public class VaccineCriteria {

    private String name;
    private String description;
    private String specie;
    private Long userId;
}
