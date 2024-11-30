package com.fho.digitalpec.api.animal.service;

import org.springframework.stereotype.Service;

import com.fho.digitalpec.api.animal.dto.AnimalStatus;
import com.fho.digitalpec.api.animal.entity.AnimalControl;
import com.fho.digitalpec.api.animal.repository.AnimalControlRepository;
import com.fho.digitalpec.api.user.service.UserService;
import com.fho.digitalpec.security.authentication.LoggedUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnimalControlService {

    private final AnimalControlRepository repository;
    private final UserService userService;

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

    public AnimalStatus getStatus() {
        AnimalControl animalControl = getAnimalControl();

        int difference = animalControl.getTotalExiting() - animalControl.getTotalReturning();

        return new AnimalStatus(animalControl.getTotalExiting(), animalControl.getTotalReturning(), difference);
    }

    private AnimalControl getAnimalControl() {
        Long loggedUserId = LoggedUser.getLoggedInUserId();

        return repository.findByUserId(loggedUserId)
                .orElse(AnimalControl.builder()
                        .user(userService.findById(loggedUserId))
                        .build());
    }
}
