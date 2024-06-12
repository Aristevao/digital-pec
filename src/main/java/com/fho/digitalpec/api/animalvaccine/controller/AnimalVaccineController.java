package com.fho.digitalpec.api.animalvaccine.controller;


import com.fho.digitalpec.api.animalvaccine.dto.AnimalVaccineDTO;
import com.fho.digitalpec.api.animalvaccine.mapper.AnimalVaccineMapper;
import com.fho.digitalpec.api.animalvaccine.service.AnimalVaccineService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("animal/vaccine")
public class AnimalVaccineController implements AnimalVaccineApi {

    private final AnimalVaccineService service;
    private final AnimalVaccineMapper mapper;

    @Override
    public void create(@Valid @RequestBody AnimalVaccineDTO dto) {
        log.info("Creating AnimalVaccine. Payload: {}.", dto);
        service.create(mapper.toEntity(dto));
    }

    @Override
    public Page<AnimalVaccineDTO> findAll(Pageable pageable) {
        log.info("Finding all AnimalVaccines.");
        return service.findAll(pageable).map(mapper::toDto);
    }
}
