package com.fho.digitalpec.api.animalvaccine.controller;

import com.fho.digitalpec.api.animalvaccine.dto.AnimalVaccineDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Animal Vaccine API", description = "API for managing animal vaccines")
public interface AnimalVaccineApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new animal vaccine", description = "Creates a new animal vaccine with the provided data")
    void create(@Valid @RequestBody AnimalVaccineDTO dto);

    @GetMapping
    @Operation(summary = "Find all animal vaccines", description = "Returns a paginated list of all animal vaccines")
    Page<AnimalVaccineDTO> findAll(Pageable pageable);
}
