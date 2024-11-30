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

        animalControlOpt.ifPresent(control -> animalControl.setId(control.getId()));

        animalControl.setUser(userService.findById(loggedUserId));

        return repository.save(animalControl);
    }

    public String animalExited() {
        AnimalControl animalControl = getAnimalControl();

        animalControl.setTotalOutside(animalControl.getTotalOutside() + 1);

        if (animalControl.getTotalOutside() > animalControl.getAnimalsQuantity()) {
            return "All animals are already outside: " + (animalControl.getTotalOutside() - 1);
        }

        repository.save(animalControl);

        return "Animal exited. Total animals outside: " + animalControl.getTotalOutside();
    }

    public String animalReturned() {
        AnimalControl animalControl = getAnimalControl();

        if (animalControl.getTotalOutside() - 1 >= 0) {
            animalControl.setTotalOutside(animalControl.getTotalOutside() - 1);

            repository.save(animalControl);

            return "Animal returned. Total animals outside: " + animalControl.getTotalOutside();
        } else {
            animalControl.setTotalOutside(0);
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
