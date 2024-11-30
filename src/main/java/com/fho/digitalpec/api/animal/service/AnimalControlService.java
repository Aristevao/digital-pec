package com.fho.digitalpec.api.animal.service;

import java.util.Optional;

import com.fho.digitalpec.api.animal.dto.AnimalControlDTO;
import com.fho.digitalpec.api.animal.entity.AnimalControl;
import com.fho.digitalpec.api.animal.mapper.AnimalControlMapper;
import com.fho.digitalpec.api.animal.repository.AnimalControlRepository;
import com.fho.digitalpec.api.user.service.UserService;
import com.fho.digitalpec.security.authentication.LoggedUser;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnimalControlService {

    private final AnimalControlRepository repository;
    private final UserService userService;
    private final AnimalService animalService;
    private final AnimalControlMapper mapper;

    public AnimalControl createAnimalControl(AnimalControlDTO dto) {
        AnimalControl animalControl = mapper.toEntity(dto);

        Long loggedUserId = LoggedUser.getLoggedInUserId();
        Optional<AnimalControl> animalControlOpt = repository.findByUserId(loggedUserId);

        if (animalControlOpt.isPresent()) {
            animalControl.setId(animalControlOpt.get().getId());
        }

        animalControl.setUser(userService.findById(loggedUserId));

        return repository.save(animalControl);
    }

    public String animalExited() {
        AnimalControl animalControl = getAnimalControl();

        animalControl.setTotalExiting(animalControl.getTotalExiting() + 1);

        repository.save(animalControl);

        return "Animal exited. Total animals outside: " + animalControl.getTotalExiting();
    }

    public String animalReturned() {
        AnimalControl animalControl = getAnimalControl();

        if (animalControl.getTotalReturning() < animalControl.getTotalExiting()) {
            animalControl.setTotalReturning(animalControl.getTotalReturning() + 1);

            repository.save(animalControl);

            return "Animal returned. Total animals inside: " + animalControl.getTotalReturning();
        } else {
            animalControl.setTotalExiting(0);
            animalControl.setTotalReturning(0);
            repository.save(animalControl);

            return "All animals have already returned.";
        }
    }

    public AnimalControl getAnimalControl() {
        Long loggedUserId = LoggedUser.getLoggedInUserId();

        return repository.findByUserId(loggedUserId)
                .orElse(AnimalControl.builder()
                        .user(userService.findById(loggedUserId))
                        .animalsQuantity(animalService.countAll())
                        .build());
    }
}
