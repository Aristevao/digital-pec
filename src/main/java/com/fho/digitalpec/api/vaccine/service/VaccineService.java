package com.fho.digitalpec.api.vaccine.service;

import java.util.List;

import com.fho.digitalpec.api.vaccine.entity.Vaccine;
import com.fho.digitalpec.api.vaccine.repository.VaccineRepository;
import com.fho.digitalpec.exception.ResourceNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VaccineService {

    private final VaccineRepository repository;
    private final MessageSource messageSource;

    public void create(Vaccine entity) {
        Vaccine vaccine = repository.save(entity);
        log.info("Vaccine '{}' was successfully created.", vaccine.getId());
    }

    public Page<Vaccine> findAll(Pageable pageable) {
        Page<Vaccine> vaccines = repository.findAll(pageable);
        log.info("Fetched {} Vaccines.", vaccines.getContent().size());
        return vaccines;
    }

    public List<Vaccine> listAll() {
        List<Vaccine> vaccines = repository.findAll();
        log.info("Fetched {} Vaccines.", vaccines.size());
        return vaccines;
    }

    public Vaccine findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource, Vaccine.class, id));
    }

    public void deleteById(Long id) {
        findById(id);
        repository.deleteById(id);
        log.info("Vaccine '{}' was successfully deleted.", id);
    }

    public void update(Long id, Vaccine entity) {
        findById(id);
        entity.setId(id);
        repository.save(entity);
    }

    public void updateName(Long id, String name) {
        Vaccine vaccine = findById(id);
        vaccine.setName(name);
        repository.save(vaccine);
        log.info("Vaccine '{}' was successfully updated.", id);
    }
}
