package com.fho.digitalpec.api.unit.service;

import static java.lang.String.format;

import java.util.List;

import com.fho.digitalpec.api.specie.service.SpecieService;
import com.fho.digitalpec.api.unit.entity.Unit;
import com.fho.digitalpec.api.unit.repository.UnitRepository;
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
public class UnitService {

    private final MessageSource messageSource;
    private final UnitRepository repository;
    private final SpecieService specieService;

    public void create(Unit entity) {
        Unit unit = repository.save(entity);
        log.info("Unit '{}' was successfully created.", unit.getId());
    }

    public Page<Unit> findAll(Pageable pageable) {
        Page<Unit> units = repository.findAll(pageable);
        log.info("Fetched {} Units.", units.getContent().size());
        return units;
    }

    public List<Unit> listAll() {
        List<Unit> units = repository.findAllByOrderByNameAsc();
        log.info("Fetched {} Units.", units.size());
        return units;
    }

    public Unit findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource, Unit.class, id));
    }

    public void deleteById(Long id) {
        findById(id);
        repository.deleteById(id);
        log.info("Unit '{}' was successfully deleted.", id);
    }

    public void update(Long id, Unit entity) {
        findById(id);
        entity.setId(id);
        repository.save(entity);
    }
}
