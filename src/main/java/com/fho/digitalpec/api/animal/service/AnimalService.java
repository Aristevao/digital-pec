package com.fho.digitalpec.api.animal.service;

import static java.lang.String.format;

import java.util.List;

import com.fho.digitalpec.api.animal.entity.Animal;
import com.fho.digitalpec.api.animal.repository.AnimalRepository;
import com.fho.digitalpec.api.specie.service.SpecieService;
import com.fho.digitalpec.exception.ConflictException;
import com.fho.digitalpec.exception.ErrorCode;
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
public class AnimalService {

    private final MessageSource messageSource;
    private final AnimalRepository repository;
    private final SpecieService specieService;

    public void create(Animal entity) {
        validateIdentificationUniqueness(entity);

        specieService.create(entity.getSpecie());

        repository.save(entity);
    }

    private void validateIdentificationUniqueness(Animal entity) {
        repository.findByIdentification(entity.getIdentification())
                .ifPresent(animal -> {
                    throw new ConflictException(ErrorCode.DUPLICATED_ANIMAL_ID,
                            format("An animal with the same identification already exists: '%s'.", entity.getIdentification()));
                });
    }

    public Page<Animal> findAll(Pageable pageable) {
        Page<Animal> animals = repository.findAll(pageable);
        log.info("Fetched {} Animals.", animals.getContent().size());
        return animals;
    }

    public List<Animal> listAll() {
        List<Animal> animals = repository.findAllByOrderByNameAsc();
        log.info("Fetched {} Animals.", animals.size());
        return animals;
    }

    public Animal findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource, Animal.class, id));
    }

    public void deleteById(Long id) {
        findById(id);
        repository.deleteById(id);
        log.info("Animal '{}' was successfully deleted.", id);
    }

    public void update(Long id, Animal entity) {
        findById(id);
        entity.setId(id);
        repository.save(entity);
    }
}
