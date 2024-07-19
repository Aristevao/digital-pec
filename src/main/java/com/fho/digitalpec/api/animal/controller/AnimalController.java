package com.fho.digitalpec.api.animal.controller;


import java.util.List;

import com.fho.digitalpec.api.animal.dto.AnimalCriteria;
import com.fho.digitalpec.api.animal.dto.AnimalDTO;
import com.fho.digitalpec.api.animal.entity.Animal;
import com.fho.digitalpec.api.animal.mapper.AnimalMapper;
import com.fho.digitalpec.api.animal.service.AnimalService;
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
@RequestMapping("animal")
public class AnimalController implements AnimalApi {

    private final AnimalService service;
    private final AnimalMapper mapper;

    @Override
    public void create(@Valid @ModelAttribute AnimalDTO dto) {
        log.info("Creating animal. Payload: {}.", dto);
        service.create(mapper.toEntity(dto));
    }

    @Override
    public void update(@PathVariable Long id, @Valid @ModelAttribute AnimalDTO dto) {
        log.info("Updating animal {}. Payload: {}.", id, dto);
        service.update(id, mapper.toEntity(dto));
    }

    @Override
    public Page<AnimalDTO> findAll(AnimalCriteria criteria, Pageable pageable) {
        log.info("Finding all animals.");
        return service.findAll(criteria, pageable).map(mapper::toDto);
    }

    @Override
    public List<SimpleDTO> listAll() {
        log.info("Listing all active animals.");
        return service.listAll().stream()
                .map(mapper::toSimpleDto)
                .toList();
    }

    @Override
    public AnimalDTO findById(@PathVariable Long id) {
        log.info("Getting animal with id: {}.", id);
        Animal animal = service.findById(id);
        return mapper.toDto(animal);
    }

    @Override
    public Integer countAnimalsByUnit(@PathVariable Long unitId) {
        log.info("Getting animal's count for unit with id: {}.", unitId);
        return service.countAnimalsByUnit(unitId);
    }

    @Override
    public void delete(@PathVariable Long id) {
        log.info("Deleting the animal with id: {}.", id);
        service.deleteById(id);
    }
}
