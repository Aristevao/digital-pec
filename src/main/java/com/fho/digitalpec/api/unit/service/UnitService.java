package com.fho.digitalpec.api.unit.service;

import static java.lang.String.format;

import java.util.List;

import com.fho.digitalpec.api.unit.entity.Unit;
import com.fho.digitalpec.api.unit.repository.UnitRepository;
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
public class UnitService {

    private final MessageSource messageSource;
    private final UnitRepository repository;
    private final UserService userService;

    @Transactional
    public void create(Unit entity) {
        validateNameUniqueness(entity, null);

        Long loggedUserId = LoggedUser.getLoggedInUserId();
        entity.setUser(userService.findById(loggedUserId));

        repository.save(entity);

//        TODO: Upload picture to S3
//        if (Objects.nonNull(dto.getPicture())) {
//            unitFileStorage.uploadImage(unit.getId(), dto.getPicture());
//        }
    }

    public void update(Long id, Unit entity) {
        Unit unit = findById(id);

        validateNameUniqueness(entity, id);

        if (unit.getAddress().getNumber().equals(entity.getAddress().getNumber()) &&
                unit.getAddress().getZipcode().equals(entity.getAddress().getZipcode())) {
            entity.setAddress(unit.getAddress());
        }

        entity.setId(id);
        entity.setUser(unit.getUser());

        repository.save(entity);
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

    private void validateNameUniqueness(Unit entity, Long id) {
        repository.findByName(entity.getName())
                .ifPresent(unit -> {
                    if (!unit.getId().equals(id)) {
                        throw new ConflictException(ErrorCode.DUPLICATED_UNIT_NAME,
                                format("An unit with the same name already exists: '%s'.", entity.getName()));
                    }
                });
    }
}
