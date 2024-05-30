package com.fho.digitalpec.api.unit.controller;


import java.util.List;

import com.fho.digitalpec.api.unit.dto.UnitDTO;
import com.fho.digitalpec.api.unit.entity.Unit;
import com.fho.digitalpec.api.unit.mapper.UnitMapper;
import com.fho.digitalpec.api.unit.service.UnitService;
import com.fho.digitalpec.utils.mapper.SimpleDTO;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("unit")
public class UnitController {

    private final UnitService service; // TODO: TESTAR
    private final UnitMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody UnitDTO dto) {
        log.info("Creating unit. Payload: {}.", dto);
        service.create(mapper.toEntity(dto));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @Valid @RequestBody UnitDTO dto) {
        log.info("Updating unit {}. Payload: {}.", id, dto);
        service.update(id, mapper.toEntity(dto));
    }

    @GetMapping
    public Page<UnitDTO> findAll(Pageable pageable) {
        log.info("Finding all units.");
        return service.findAll(pageable).map(mapper::toDto);
    }

    @GetMapping("list")
    public List<SimpleDTO> listAll() {
        log.info("Listing all active units.");
        return service.listAll().stream()
                .map(mapper::toSimpleDto)
                .toList();
    }

    @GetMapping("{id}")
    public UnitDTO findById(@PathVariable Long id) {
        log.info("Getting unit with id: {}.", id);
        Unit unit = service.findById(id);
        return mapper.toDto(unit);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("Deleting the unit with id: {}.", id);
        service.deleteById(id);
    }
}
