package com.fho.digitalpec.api.user.controller;


import java.util.List;

import com.fho.digitalpec.api.user.dto.UserDTO;
import com.fho.digitalpec.api.user.entity.User;
import com.fho.digitalpec.api.user.mapper.UserMapper;
import com.fho.digitalpec.api.user.service.UserService;
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
@RequestMapping("user")
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody UserDTO dto) {
        log.info("Creating user. Payload: {}.", dto);
        service.create(mapper.toEntity(dto));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @Valid @RequestBody UserDTO dto) {
        log.info("Updating user {}. Payload: {}.", id, dto);
        service.update(id, mapper.toEntity(dto));
    }

    @GetMapping
    public Page<UserDTO> findAll(Pageable pageable) {
        log.info("Finding all users.");
        return service.findAll(pageable).map(mapper::toDto);
    }

    @GetMapping("list")
    public List<SimpleDTO> listAll() {
        log.info("Listing all active users.");
        return service.listAll().stream()
                .map(mapper::toSimpleDto)
                .toList();
    }

    @GetMapping("{id}")
    public UserDTO findById(@PathVariable Long id) {
        log.info("Getting user with id: {}.", id);
        User user = service.findById(id);
        return mapper.toDto(user);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("Deleting the user with id: {}.", id);
        service.deleteById(id);
    }
}
