package com.fho.digitalpec.api.animalvaccine.controller;


import com.fho.digitalpec.api.animalvaccine.dto.AnimalVaccineCriteria;
import com.fho.digitalpec.api.animalvaccine.dto.AnimalVaccineDTO;
import com.fho.digitalpec.api.animalvaccine.entity.AnimalVaccine;
import com.fho.digitalpec.api.animalvaccine.mapper.AnimalVaccineMapper;
import com.fho.digitalpec.api.animalvaccine.service.AnimalVaccineService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
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
        service.create(mapper.toEntity(dto), dto);
    }

    @Override
    public void update(@PathVariable Long id, @Valid @RequestBody AnimalVaccineDTO dto) {
        log.info("Updating AnimalVaccine {}. Payload: {}.", id, dto);
        service.update(id, dto);
    }

    @Override
    public Page<AnimalVaccineDTO> findAll(AnimalVaccineCriteria criteria, Pageable pageable) {
        log.info("Finding all AnimalVaccines.");
        return service.findAll(criteria, pageable).map(mapper::toDto);
    }

    @Override
    public AnimalVaccineDTO findById(@PathVariable Long id) {
        log.info("Getting AnimalVaccine with id: {}.", id);
        AnimalVaccine AnimalVaccine = service.findById(id);
        return mapper.toDto(AnimalVaccine);
    }

    @Override
    public void delete(@PathVariable Long id) {
        log.info("Deleting the AnimalVaccine with id: {}.", id);
        service.deleteById(id);
    }
}
