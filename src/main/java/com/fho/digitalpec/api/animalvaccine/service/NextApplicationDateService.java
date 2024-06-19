package com.fho.digitalpec.api.animalvaccine.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import com.fho.digitalpec.api.animalvaccine.dto.AnimalVaccineDTO;
import com.fho.digitalpec.api.animalvaccine.entity.AnimalVaccine;
import com.fho.digitalpec.api.animalvaccine.entity.NextApplicationDate;
import com.fho.digitalpec.api.animalvaccine.repository.NextApplicationDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NextApplicationDateService {

    private final NextApplicationDateRepository repository;
    private final AnimalVaccineService animalVaccineService;

    @Autowired
    public NextApplicationDateService(NextApplicationDateRepository repository, @Lazy AnimalVaccineService animalVaccineService) {
        this.repository = repository;
        this.animalVaccineService = animalVaccineService;
    }

    @Transactional
    public List<NextApplicationDate> create(AnimalVaccine entity, AnimalVaccineDTO dto) {
        List<LocalDate> nextApplicationLocalDates = dto.getNextApplicationDates();
        if (!nextApplicationLocalDates.isEmpty()) {
            return nextApplicationLocalDates.stream()
                    .map(localDate -> NextApplicationDate.builder()
                            .animalVaccine(entity)
                            .applicationDate(localDate)
                            .build())
                    .toList();
        }
        return Collections.emptyList();
    }

    @Transactional
    public void create(AnimalVaccine entity, List<LocalDate> nextApplicationLocalDates) {
        List<NextApplicationDate> nextApplicationDates = nextApplicationLocalDates.stream()
                .map(localDate -> NextApplicationDate.builder()
                        .animalVaccine(entity)
                        .applicationDate(localDate)
                        .build())
                .toList();
        repository.saveAll(nextApplicationDates);
    }

    public void update(AnimalVaccine entity, List<LocalDate> newNextApplicationDate) {
        List<LocalDate> existingNextApplicationDates = findNextApplicationDatesByAnimalVaccine(entity);
        deleteRemovedApplicationDates(entity.getId(), newNextApplicationDate, existingNextApplicationDates);
        addNewApplicationDates(entity, newNextApplicationDate, existingNextApplicationDates);
    }

    public List<LocalDate> findNextApplicationDatesByAnimalVaccine(AnimalVaccine entity) {
        return repository.findByAnimalVaccine(entity)
                .stream()
                .map(NextApplicationDate::getApplicationDate)
                .toList();
    }

    private void deleteRemovedApplicationDates(Long entityId, List<LocalDate> newNextApplicationDate, List<LocalDate> existingNextApplicationDates) {
        List<LocalDate> datesToRemove = existingNextApplicationDates.stream()
                .filter(existingNextApplicationDate -> !newNextApplicationDate.contains(existingNextApplicationDate))
                .toList();

        repository.deleteByAnimalVaccineIdAndApplicationDateIn(entityId, datesToRemove);
    }

    private void addNewApplicationDates(AnimalVaccine entity, List<LocalDate> newNextApplicationDate, List<LocalDate> existingNextApplicationDates) {
        List<LocalDate> datesToAdd = newNextApplicationDate.stream()
                .filter(newDate -> !existingNextApplicationDates.contains(newDate))
                .toList();

        datesToAdd.forEach(date -> {
            NextApplicationDate nextApplicationDate = NextApplicationDate.builder()
                    .applicationDate(date)
                    .animalVaccine(entity)
                    .build();
            entity.getNextApplicationDates().add(nextApplicationDate);
        });

        animalVaccineService.save(entity);
    }
}
