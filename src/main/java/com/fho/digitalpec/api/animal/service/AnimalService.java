package com.fho.digitalpec.api.animal.service;

import static java.lang.String.format;

import java.util.List;

import com.fho.digitalpec.api.animal.dto.AnimalCriteria;
import com.fho.digitalpec.api.animal.entity.Animal;
import com.fho.digitalpec.api.animal.repository.AnimalRepository;
import com.fho.digitalpec.api.animal.repository.AnimalSpecification;
import com.fho.digitalpec.api.specie.service.SpecieService;
import com.fho.digitalpec.api.user.service.UserService;
import com.fho.digitalpec.exception.ConflictException;
import com.fho.digitalpec.exception.ErrorCode;
import com.fho.digitalpec.exception.ResourceNotFoundException;
import com.fho.digitalpec.security.authentication.LoggedUser;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnimalService {

    private final MessageSource messageSource;
    private final AnimalRepository repository;
    private final SpecieService specieService;
    private final UserService userService;

    @Transactional
    public void create(Animal entity) {
        validateIdentificationUniqueness(entity, null);

        Long loggedUserId = LoggedUser.getLoggedInUserId();
        entity.setUser(userService.findById(loggedUserId));

        specieService.create(entity);

        repository.save(entity);

//        TODO: Upload picture to S3
//        if (Objects.nonNull(dto.getPicture())) {
//            animalFileStorage.uploadImage(animal.getId(), dto.getPicture());
//        }
    }

    public void update(Long id, Animal entity) {
        Animal animal = findById(id);

        validateIdentificationUniqueness(entity, id);

        entity.setId(id);
        entity.setUser(animal.getUser());

        repository.save(entity);
    }

    public Page<Animal> findAll(AnimalCriteria criteria, Pageable pageable) {
        AnimalSpecification specification = new AnimalSpecification(criteria);
        Page<Animal> animals = repository.findAll(specification, pageable);

        log.info("Fetched {} Animals.", animals.getContent().size());
        return animals;
    }

    public List<Animal> listAll() {
        Long loggedUserId = LoggedUser.getLoggedInUserId();
        List<Animal> animals = repository.findAllByUserIdOrderByNameAsc(loggedUserId);
        log.info("Fetched {} Animals.", animals.size());
        return animals;
    }

    public Animal findById(Long id) {
        Long loggedUserId = LoggedUser.getLoggedInUserId();
        return repository.findByIdAndUserId(id, loggedUserId)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource, Animal.class, id));
    }

    public void deleteById(Long id) {
        findById(id);
        repository.deleteById(id);
        log.info("Animal '{}' was successfully deleted.", id);
    }

    private void validateIdentificationUniqueness(Animal entity, Long id) {
        repository.findByIdentification(entity.getIdentification())
                .ifPresent(animal -> {
                    if (!animal.getId().equals(id)) {
                        throw new ConflictException(ErrorCode.DUPLICATED_ANIMAL_ID,
                                format("An animal with the same identification already exists: '%s'.", entity.getIdentification()));
                    }
                });
    }
}
