package com.fho.digitalpec.api.vaccine.controller;

import java.util.List;

import com.fho.digitalpec.api.vaccine.dto.VaccineCriteria;
import com.fho.digitalpec.api.vaccine.dto.VaccineDTO;
import com.fho.digitalpec.utils.mapper.SimpleDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Vaccine API", description = "API for managing vaccines")
public interface VaccineApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new vaccine", description = "Creates a new vaccine with the provided data")
    void create(@Valid @RequestBody VaccineDTO dto);

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update an existing vaccine", description = "Updates the details of an existing vaccine by its ID")
    void update(@Parameter(description = "ID of the vaccine to be updated") @PathVariable Long id,
                @Valid @RequestBody VaccineDTO dto);

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update vaccine name", description = "Updates the name of an existing vaccine by its ID")
    void updateName(@Parameter(description = "ID of the vaccine to be updated") @PathVariable Long id,
                    @Valid @RequestBody VaccineDTO dto);

    @GetMapping
    @Operation(summary = "Find all vaccines", description = "Returns a paginated list of all vaccines")
    Page<VaccineDTO> findAll(VaccineCriteria criteria, Pageable pageable);

    @GetMapping("list")
    @Operation(summary = "List all active vaccines", description = "Returns a list of all active vaccines")
    List<SimpleDTO> listAll();

    @GetMapping("{id}")
    @Operation(summary = "Get vaccine by ID", description = "Returns the details of a vaccine by its ID")
    VaccineDTO findById(@Parameter(description = "ID of the vaccine to be fetched") @PathVariable Long id);

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a vaccine", description = "Deletes a vaccine by its ID")
    void delete(@Parameter(description = "ID of the vaccine to be deleted") @PathVariable Long id);
}
