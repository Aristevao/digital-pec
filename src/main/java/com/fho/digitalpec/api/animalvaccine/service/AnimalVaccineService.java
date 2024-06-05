package com.fho.digitalpec.api.animalvaccine.service;

import com.fho.digitalpec.api.animalvaccine.entity.AnimalVaccine;
import com.fho.digitalpec.api.animalvaccine.repository.AnimalVaccineRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnimalVaccineService {

    private final AnimalVaccineRepository repository;

    public void create(AnimalVaccine entity) {
        repository.save(entity);
    }

    public Page<AnimalVaccine> findAll(Pageable pageable) {
        Page<AnimalVaccine> animalVaccineLists = repository.findAll(pageable);
        log.info("Fetched {} AnimalVaccines.", animalVaccineLists.getContent().size());
        return animalVaccineLists;
    }
}
