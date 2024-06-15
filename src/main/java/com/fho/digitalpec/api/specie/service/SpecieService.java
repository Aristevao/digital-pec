package com.fho.digitalpec.api.specie.service;

import java.util.ArrayList;
import java.util.List;

import com.fho.digitalpec.api.animal.entity.Animal;
import com.fho.digitalpec.api.specie.entity.Specie;
import com.fho.digitalpec.api.specie.repository.SpecieRepository;
import com.fho.digitalpec.api.vaccine.dto.VaccineDTO;
import com.fho.digitalpec.api.vaccine.entity.Vaccine;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpecieService {

    private final SpecieRepository repository;

    public Specie create(Animal entity) {
        Specie existingSpecie = repository.findByName(entity.getSpecie().getName());

        if (existingSpecie == null) {
            Specie specie = Specie.builder()
                    .name(entity.getSpecie().getName())
                    .user(entity.getUser())
                    .build();

            Specie persisted = repository.save(specie);
            log.info("Specie '{}' was successfully created.", persisted.getId());

            return persisted;
        }

        return existingSpecie;
    }

    public void create(Vaccine entity, VaccineDTO dto) {
        List<Specie> newSpecies = new ArrayList<>();

        dto.getSpecies().forEach(newSpecie -> {
            Specie existingSpecie = repository.findByName(newSpecie);
            if (existingSpecie == null) {
                Specie specie = Specie.builder()
                        .name(newSpecie)
                        .user(entity.getUser())
                        .vaccine(entity)
                        .build();

                newSpecies.add(specie);
            } else if (existingSpecie.getVaccine() == null) {
                existingSpecie.setVaccine(entity);
                newSpecies.add(existingSpecie);
            }
        });
        repository.saveAll(newSpecies);
    }

    public List<Specie> listAll() {
        List<Specie> species = repository.findAllByOrderByNameAsc();
        log.info("Fetched {} Species.", species.size());
        return species;
    }
}
