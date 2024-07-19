package com.fho.digitalpec.api.unit.dto;

import com.fho.digitalpec.api.address.entity.Address;

import lombok.Data;

@Data
public class UnitResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Address address;
    private String picture;
}