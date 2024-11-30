package com.fho.digitalpec.api.animal.controller;

import java.util.List;

import com.fho.digitalpec.api.animal.dto.AnimalCriteria;
import com.fho.digitalpec.api.animal.dto.AnimalDTO;
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

@Tag(name = "Animal API", description = "API for managing animals")
public interface AnimalApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new animal", description = "Creates a new animal with the provided data")
    void create(@Valid @RequestBody AnimalDTO dto);

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update an existing animal", description = "Updates the details of an existing animal by its ID")
    void update(@Parameter(description = "ID of the animal to be updated") @PathVariable Long id,
                @Valid @RequestBody AnimalDTO dto);

    @GetMapping
    @Operation(summary = "Find all animals", description = "Returns a paginated list of all animals")
    Page<AnimalDTO> findAll(AnimalCriteria criteria, Pageable pageable);

    @GetMapping("list")
    @Operation(summary = "List all active animals", description = "Returns a list of all active animals")
    List<SimpleDTO> listAll();

    @GetMapping("{id}")
    @Operation(summary = "Get animal by ID", description = "Returns the details of an animal by its ID")
    AnimalDTO findById(@Parameter(description = "ID of the animal to be fetched") @PathVariable Long id);

    @GetMapping("unit/{unitId}")
    @Operation(summary = "Get animal count by unit ID", description = "Returns the animal count of an unit by its ID")
    Integer countAnimalsByUnit(@PathVariable Long unitId);

    @GetMapping("count")
    @Operation(summary = "Get animal count", description = "Returns the animal count")
    Integer countAll();

    @GetMapping("count/specie")
    @Operation(summary = "Get animal count by specie", description = "Returns the animal count by specie")
    Integer countBySpecie();

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an animal", description = "Deletes an animal by its ID")
    void delete(@Parameter(description = "ID of the animal to be deleted") @PathVariable Long id);
}
