package com.fho.digitalpec.api.animal.dto;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalControlDTO {

    private int totalExiting;
    private int totalReturning;
    private int difference;

    @NotNull
    private int animalsQuantity;
}