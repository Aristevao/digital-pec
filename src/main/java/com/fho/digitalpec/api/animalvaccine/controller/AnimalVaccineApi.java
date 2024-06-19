package com.fho.digitalpec.api.animalvaccine.controller;

import com.fho.digitalpec.api.animalvaccine.dto.AnimalVaccineCriteria;
import com.fho.digitalpec.api.animalvaccine.dto.AnimalVaccineDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Animal Vaccine API", description = "API for managing animal vaccines")
public interface AnimalVaccineApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new animal vaccine", description = "Creates a new animal vaccine with the provided data")
    void create(@Valid @RequestBody AnimalVaccineDTO dto);

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update an existing animal vaccine", description = "Updates the details of an existing animal vaccine by its ID")
    void update(@Parameter(description = "ID of the animal vaccine to be updated") @PathVariable Long id,
                @Valid @RequestBody AnimalVaccineDTO dto);

    @GetMapping
    @Operation(summary = "Find all animal vaccines", description = "Returns a paginated list of all animal vaccines")
    Page<AnimalVaccineDTO> findAll(AnimalVaccineCriteria criteria, Pageable pageable);

    @GetMapping("{id}")
    @Operation(summary = "Get animal vaccine by ID", description = "Returns the details of a animal vaccine by its ID")
    AnimalVaccineDTO findById(@Parameter(description = "ID of the animal vaccine to be fetched") @PathVariable Long id);

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a animal vaccine", description = "Deletes a animal vaccine by its ID")
    void delete(@Parameter(description = "ID of the animal vaccine to be deleted") @PathVariable Long id);
}
