package com.fho.digitalpec.api.specie.controller;

import java.util.List;

import com.fho.digitalpec.api.specie.dto.SpecieDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Specie API", description = "API for managing species")
public interface SpecieApi {

    @GetMapping("list")
    @Operation(summary = "List all active species", description = "Returns a list of all active species")
    List<SpecieDTO> listAll();
}
