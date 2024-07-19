package com.fho.digitalpec.api.unit.controller;

import java.util.List;

import com.fho.digitalpec.api.unit.dto.UnitDTO;
import com.fho.digitalpec.api.unit.dto.UnitResponseDTO;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Unit API", description = "API for managing units")
public interface UnitApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new unit", description = "Creates a new unit with the provided data")
    void create(@Valid @RequestBody UnitDTO dto);

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update an existing unit", description = "Updates the details of an existing unit by its ID")
    void update(@Parameter(description = "ID of the unit to be updated") @PathVariable Long id,
                @Valid @RequestBody UnitDTO dto);

    @GetMapping
    @Operation(summary = "Find all units", description = "Returns a paginated list of all units")
    Page<UnitResponseDTO> findAll(Pageable pageable);

    @GetMapping("list")
    @Operation(summary = "List all active units", description = "Returns a list of all active units")
    List<SimpleDTO> listAll();

    @GetMapping("{id}")
    @Operation(summary = "Get unit by ID", description = "Returns the details of a unit by its ID")
    UnitDTO findById(@Parameter(description = "ID of the unit to be fetched") @PathVariable Long id);

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a unit", description = "Deletes a unit by its ID")
    void delete(@Parameter(description = "ID of the unit to be deleted") @PathVariable Long id);
}
