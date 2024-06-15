package com.fho.digitalpec.api.vaccine.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class VaccineDTO {

    private Long id;

    @NotBlank
    @Size(max = 80)
    private String name;

    @Size(max = 500)
    private String description;

    private List<String> species;
}
