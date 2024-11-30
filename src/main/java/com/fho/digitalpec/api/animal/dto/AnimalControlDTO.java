package com.fho.digitalpec.api.animal.dto;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalControlDTO {

    private int totalOutside;
    private int totalInside;

    @NotNull
    private int animalsQuantity;
}