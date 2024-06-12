package com.fho.digitalpec.api.animal.controller;

import java.util.List;

import com.fho.digitalpec.api.animal.dto.AnimalDTO;
import com.fho.digitalpec.utils.mapper.SimpleDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Animal API", description = "API for managing animals")
public interface AnimalApi {

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new animal", description = "Creates a new animal with the provided data")
    void create(@Valid @RequestBody AnimalDTO dto);

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update an existing animal", description = "Updates the details of an existing animal by its ID")
    void update(@Parameter(description = "ID of the animal to be updated") @PathVariable Long id,
                @Valid @RequestBody AnimalDTO dto);

    @Operation(summary = "Find all animals", description = "Returns a paginated list of all animals")
    Page<AnimalDTO> findAll(Pageable pageable);

    @Operation(summary = "List all active animals", description = "Returns a list of all active animals")
    List<SimpleDTO> listAll();

    @Operation(summary = "Get animal by ID", description = "Returns the details of an animal by its ID")
    AnimalDTO findById(@Parameter(description = "ID of the animal to be fetched") @PathVariable Long id);

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an animal", description = "Deletes an animal by its ID")
    void delete(@Parameter(description = "ID of the animal to be deleted") @PathVariable Long id);
}