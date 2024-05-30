package com.fho.digitalpec.api.animal.controller;


import java.util.List;

import com.fho.digitalpec.api.animal.dto.AnimalDTO;
import com.fho.digitalpec.api.animal.entity.Animal;
import com.fho.digitalpec.api.animal.mapper.AnimalMapper;
import com.fho.digitalpec.api.animal.service.AnimalService;
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
@RequestMapping("animal")
public class AnimalController {

    private final AnimalService service;
    private final AnimalMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody AnimalDTO dto) {
        log.info("Creating animal. Payload: {}.", dto);
        service.create(mapper.toEntity(dto));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @Valid @RequestBody AnimalDTO dto) {
        log.info("Updating animal {}. Payload: {}.", id, dto);
        service.update(id, mapper.toEntity(dto));
    }

    @GetMapping
    public Page<AnimalDTO> findAll(Pageable pageable) {
        log.info("Finding all animals.");
        return service.findAll(pageable).map(mapper::toDto);
    }

    @GetMapping("list")
    public List<SimpleDTO> listAll() {
        log.info("Listing all active animals.");
        return service.listAll().stream()
                .map(mapper::toSimpleDto)
                .toList();
    }

    @GetMapping("{id}")
    public AnimalDTO findById(@PathVariable Long id) {
        log.info("Getting animal with id: {}.", id);
        Animal animal = service.findById(id);
        return mapper.toDto(animal);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("Deleting the animal with id: {}.", id);
        service.deleteById(id);
    }
}
