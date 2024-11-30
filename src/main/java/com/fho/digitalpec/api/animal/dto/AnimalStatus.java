package com.fho.digitalpec.api.animal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalStatus {

    private int totalExiting;
    private int totalReturning;
    private int difference;
}