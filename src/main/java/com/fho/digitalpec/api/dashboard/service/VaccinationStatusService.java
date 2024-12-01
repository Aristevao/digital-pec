package com.fho.digitalpec.api.dashboard.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fho.digitalpec.api.animal.entity.Animal;
import com.fho.digitalpec.api.animal.repository.AnimalRepository;
import com.fho.digitalpec.api.animalvaccine.entity.AnimalVaccine;
import com.fho.digitalpec.api.animalvaccine.entity.NextApplicationDate;
import com.fho.digitalpec.api.animalvaccine.repository.AnimalVaccineRepository;
import com.fho.digitalpec.api.dashboard.dto.VaccinationStatusDTO;
import com.fho.digitalpec.api.vaccine.entity.Vaccine;
import com.fho.digitalpec.security.authentication.LoggedUser;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VaccinationStatusService {

    private final AnimalVaccineRepository repository;
    private final AnimalRepository animalRepository;


    public List<VaccinationStatusDTO> getVaccinationStatus() {
        // Buscar todos os animais do usuário
        List<Animal> animals = animalRepository.findByUserId(LoggedUser.getLoggedInUserId());

        // Inicializando a lista de status de vacinação
        List<VaccinationStatusDTO> vaccinationStatuses = new ArrayList<>();

        for (Animal animal : animals) {
            VaccinationStatusDTO statusDTO = new VaccinationStatusDTO();
            statusDTO.setAnimalName(animal.getName());
            statusDTO.setAnimalIdentification(animal.getIdentification());

            // Inicializando a lista de vacinas para o animal
            List<VaccinationStatusDTO.VaccineStatusDTO> vaccineStatuses = new ArrayList<>();

            // Obtendo as vacinas aplicadas ao animal
            List<AnimalVaccine> animalVaccines = repository.findByAnimalId(animal.getId());

            for (AnimalVaccine animalVaccine : animalVaccines) {
                Vaccine vaccine = animalVaccine.getVaccine();

                // Preenchendo os dados de status de cada vacina
                VaccinationStatusDTO.VaccineStatusDTO vaccineStatusDTO = new VaccinationStatusDTO.VaccineStatusDTO();
                vaccineStatusDTO.setVaccineName(vaccine.getName());
                vaccineStatusDTO.setIsVaccinated(animalVaccine.getCompleted());
                vaccineStatusDTO.setLastVaccineDate(animalVaccine.getApplicationDate());

                // Buscando a próxima data de aplicação, se existir
                Optional<NextApplicationDate> nextApplication = animalVaccine.getNextApplicationDates()
                        .stream()
                        .filter(date -> date.getApplicationDate().isAfter(LocalDate.now()))
                        .findFirst();

                nextApplication.ifPresent(next -> vaccineStatusDTO.setNextVaccineDate(next.getApplicationDate()));

                vaccineStatuses.add(vaccineStatusDTO);
            }

            // Adicionando a lista de vacinas no DTO do animal
            statusDTO.setVaccineStatuses(vaccineStatuses);

            // Apenas adiciona o animal se ele tiver vacinas
            if (!vaccineStatuses.isEmpty()) {
                vaccinationStatuses.add(statusDTO);
            }
        }

        return vaccinationStatuses;
    }
}
