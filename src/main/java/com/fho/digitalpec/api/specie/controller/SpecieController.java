package com.fho.digitalpec.api.specie.controller;


import java.util.List;

import com.fho.digitalpec.api.specie.dto.SpecieDTO;
import com.fho.digitalpec.api.specie.mapper.SpecieMapper;
import com.fho.digitalpec.api.specie.service.SpecieService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("specie")
public class SpecieController implements SpecieApi {

    private final SpecieService service;
    private final SpecieMapper mapper;

    @Override
    public List<SpecieDTO> listAll() {
        log.info("Listing all active species.");
        return service.listAll().stream()
                .map(mapper::toDto)
                .toList();
    }
}
