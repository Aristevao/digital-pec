package com.fho.digitalpec.api.specie.service;

import java.util.List;

import com.fho.digitalpec.api.animal.entity.Animal;
import com.fho.digitalpec.api.specie.entity.Specie;
import com.fho.digitalpec.api.specie.repository.SpecieRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpecieService {

    private final SpecieRepository repository;

    public void create(Animal entity) {
        Specie existingSpecie = repository.findByName(entity.getSpecie());

        if (existingSpecie == null) {
            Specie specie = Specie.builder()
                    .name(entity.getSpecie())
                    .user(entity.getUser())
                    .build();

            Specie persisted = repository.save(specie);
            log.info("Specie '{}' was successfully created.", persisted.getId());
        }
    }

    public List<Specie> listAll() {
        List<Specie> species = repository.findAllByOrderByNameAsc();
        log.info("Fetched {} Species.", species.size());
        return species;
    }
}
