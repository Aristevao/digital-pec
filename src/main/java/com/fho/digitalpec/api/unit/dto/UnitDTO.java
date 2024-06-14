package com.fho.digitalpec.api.unit.dto;

import com.fho.digitalpec.api.address.entity.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UnitDTO {

    private Long id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    private Address address;

    private MultipartFile picture;
}