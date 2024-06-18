package com.fho.digitalpec.api.vaccine.service;

import static java.lang.String.format;

import java.util.List;

import com.fho.digitalpec.api.user.service.UserService;
import com.fho.digitalpec.api.vaccine.dto.VaccineCriteria;
import com.fho.digitalpec.api.vaccine.dto.VaccineDTO;
import com.fho.digitalpec.api.vaccine.entity.Vaccine;
import com.fho.digitalpec.api.vaccine.repository.VaccineRepository;
import com.fho.digitalpec.api.vaccine.repository.VaccineSpecification;
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
public class VaccineService {

    private final VaccineRepository repository;
    private final MessageSource messageSource;
    private final UserService userService;

    @Transactional
    public void create(Vaccine entity, VaccineDTO dto) {
        validateNameUniqueness(entity, null);

        Long loggedUserId = LoggedUser.getLoggedInUserId();
        entity.setUser(userService.findById(loggedUserId));

        Vaccine vaccine = repository.save(entity);
        log.info("Vaccine '{}' was successfully created.", vaccine.getId());
    }

    public void update(Long id, Vaccine entity) {
        findById(id);

        validateNameUniqueness(entity, id);

        entity.setId(id);
        repository.save(entity);
    }

    public Page<Vaccine> findAll(VaccineCriteria criteria, Pageable pageable) {
        VaccineSpecification specification = new VaccineSpecification(criteria);
        Page<Vaccine> vaccines = repository.findAll(specification, pageable);

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

    public void updateName(Long id, String name) {
        Vaccine vaccine = findById(id);
        vaccine.setName(name);
        repository.save(vaccine);
        log.info("Vaccine '{}' was successfully updated.", id);
    }

    private void validateNameUniqueness(Vaccine entity, Long id) {
        repository.findByName(entity.getName())
                .ifPresent(vaccine -> {
                    if (!vaccine.getId().equals(id)) {
                        throw new ConflictException(ErrorCode.DUPLICATED_UNIT_NAME,
                                format("An vaccine with the same name already exists: '%s'.", entity.getName()));
                    }
                });
    }
}
