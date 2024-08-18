package com.fho.digitalpec.api.vaccine.controller;


import java.util.List;

import com.fho.digitalpec.api.vaccine.dto.VaccineCriteria;
import com.fho.digitalpec.api.vaccine.dto.VaccineDTO;
import com.fho.digitalpec.api.vaccine.mapper.VaccineMapper;
import com.fho.digitalpec.api.vaccine.service.VaccineService;
import com.fho.digitalpec.utils.mapper.SimpleDTO;
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
@RequestMapping("vaccine")
public class VaccineController implements VaccineApi {

    private final VaccineService service;
    private final VaccineMapper mapper;

    @Override
    public void create(@Valid @RequestBody VaccineDTO dto) {
        log.info("Creating vaccine. Payload: {}.", dto);
        service.create(mapper.toEntity(dto), dto);
    }

    @Override
    public void update(@PathVariable Long id, @Valid @RequestBody VaccineDTO dto) {
        log.info("Updating vaccine {}. Payload: {}.", id, dto);
        service.update(id, mapper.toEntity(dto), dto);
    }

    @Override
    public void updateName(@PathVariable Long id, @Valid @RequestBody VaccineDTO dto) {
        log.info("Updating vaccine {}. Payload: {}.", id, dto);
        service.updateName(id, dto.getName());
    }

    @Override
    public Page<VaccineDTO> findAll(VaccineCriteria criteria, Pageable pageable) {
        log.info("Finding all vaccines.");
        return service.findAll(criteria, pageable);
    }

    @Override
    public List<SimpleDTO> listAll() {
        log.info("Listing all active vaccines.");
        return service.listAll().stream()
                .map(mapper::toSimpleDto)
                .toList();
    }

    @Override
    public VaccineDTO findById(@PathVariable Long id) {
        log.info("Getting vaccine with id: {}.", id);
        return service.findById(id);
    }

    @Override
    public void delete(@PathVariable Long id) {
        log.info("Deleting the vaccine with id: {}.", id);
        service.deleteById(id);
    }
}
