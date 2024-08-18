package com.fho.digitalpec.api.vaccine.service;

import java.util.List;

import com.fho.digitalpec.api.specie.entity.Specie;
import com.fho.digitalpec.api.specie.service.SpecieService;
import com.fho.digitalpec.api.vaccine.dto.VaccineDTO;
import com.fho.digitalpec.api.vaccine.entity.Vaccine;
import com.fho.digitalpec.api.vaccine.entity.VaccineSpecie;
import com.fho.digitalpec.api.vaccine.repository.VaccineSpecieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VaccineSpecieService {

    private final VaccineSpecieRepository repository;
    private final SpecieService specieService;

    @Transactional
    public void create(Vaccine entity, VaccineDTO dto) {
        List<Specie> species = specieService.create(entity, dto);
        List<VaccineSpecie> vaccineSpecies = species.stream()
                .map(specie -> VaccineSpecie.builder()
                        .specie(specie)
                        .vaccine(entity)
                        .build())
                .toList();
        repository.saveAll(vaccineSpecies);
    }

    @Transactional
    public void update(Vaccine entity, VaccineDTO dto) {
        deleteByVaccineId(entity.getId());
        create(entity, dto);
    }

    public List<VaccineSpecie> findByVaccineIdIn(List<Long> vaccineIds) {
        return repository.findByVaccineIdIn(vaccineIds);
    }

    public List<VaccineSpecie> findByVaccineId(Long vaccineId) {
        return repository.findByVaccineId(vaccineId);
    }

    public void deleteByVaccineId(Long vaccineId) {
        List<VaccineSpecie> vaccineSpecies = findByVaccineId(vaccineId);
        repository.deleteByIdIn(vaccineSpecies.stream()
                .map(VaccineSpecie::getId)
                .toList());
    }
}
