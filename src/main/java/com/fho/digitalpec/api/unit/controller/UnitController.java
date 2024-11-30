package com.fho.digitalpec.api.unit.controller;


import java.util.List;

import com.fho.digitalpec.api.unit.dto.UnitDTO;
import com.fho.digitalpec.api.unit.dto.UnitResponseDTO;
import com.fho.digitalpec.api.unit.entity.Unit;
import com.fho.digitalpec.api.unit.mapper.UnitMapper;
import com.fho.digitalpec.api.unit.service.UnitService;
import com.fho.digitalpec.utils.mapper.SimpleDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("unit")
public class UnitController implements UnitApi {

    private final UnitService service;
    private final UnitMapper mapper;

    @Override
    public void create(@Valid @ModelAttribute UnitDTO dto) {
        log.info("Creating unit. Payload: {}.", dto.getName());
        service.create(mapper.toEntity(dto));
    }

    @Override
    public void update(@PathVariable Long id, @Valid @ModelAttribute UnitDTO dto) {
        log.info("Updating unit {}. Payload: {}.", id, dto.getName());
        service.update(id, mapper.toEntity(dto));
    }

    @Override
    public Page<UnitResponseDTO> findAll(Pageable pageable) {
        log.info("Finding all units.");
        return service.findAll(pageable).map(mapper::toResponseDto);
    }

    @Override
    public List<SimpleDTO> listAll() {
        log.info("Listing all active units.");
        return service.listAll().stream()
                .map(mapper::toSimpleDto)
                .toList();
    }

    @Override
    public Integer countAll() {
        log.info("Getting unit's count");
        return service.countAll();
    }

    @Override
    public UnitResponseDTO findById(@PathVariable Long id) {
        log.info("Getting unit with id: {}.", id);
        Unit unit = service.findById(id);
        return mapper.toResponseDto(unit);
    }

    @Override
    public void delete(@PathVariable Long id) {
        log.info("Deleting the unit with id: {}.", id);
        service.deleteById(id);
    }
}
