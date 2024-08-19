package com.fho.digitalpec.api.animalvaccine.service;

import static java.lang.Boolean.FALSE;

import java.time.LocalDate;
import java.util.List;

import com.fho.digitalpec.api.animal.service.AnimalService;
import com.fho.digitalpec.api.animalvaccine.dto.AnimalVaccineCriteria;
import com.fho.digitalpec.api.animalvaccine.dto.AnimalVaccineDTO;
import com.fho.digitalpec.api.animalvaccine.entity.AnimalVaccine;
import com.fho.digitalpec.api.animalvaccine.entity.NextApplicationDate;
import com.fho.digitalpec.api.animalvaccine.repository.AnimalVaccineRepository;
import com.fho.digitalpec.api.animalvaccine.repository.AnimalVaccineSpecification;
import com.fho.digitalpec.api.vaccine.service.VaccineService;
import com.fho.digitalpec.exception.ResourceNotFoundException;
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
public class AnimalVaccineService {

    private final MessageSource messageSource;
    private final AnimalVaccineRepository repository;
    private final NextApplicationDateService nextApplicationDateService;
    private final AnimalService animalService;
    private final VaccineService vaccineService;

    /* TODO:
        - Criar notificationCount: notificações não lidas
            - front atualiza a cada x tempo
        - Verificar se falta algum CRUD pra ajustar
        - Vaccine não cria nova SPECIE. Cria somente a primeira. Talvez considerar refatorar pra ficar igual AnimalVaccine.
     */

    @Transactional
    public void create(AnimalVaccine entity, AnimalVaccineDTO dto) {
        entity.setAnimal(animalService.findById(entity.getAnimal().getId()));
        entity.setVaccine(vaccineService.findOne(entity.getVaccine().getId()));

        if (entity.getApplicationDate() == null) {
            entity.setApplicationDate(LocalDate.now());
        }

        if (!dto.getNextApplicationDates().isEmpty()) {
            entity.setCompleted(FALSE);
        }

        List<NextApplicationDate> nextApplicationDates = nextApplicationDateService.create(entity, dto);
        entity.setNextApplicationDates(nextApplicationDates);

        repository.save(entity);
    }

    public void update(Long id, AnimalVaccineDTO dto) {
        AnimalVaccine entity = findById(id);

        if (dto.getApplicationDate() != null) {
            entity.setApplicationDate(dto.getApplicationDate());
        }

        AnimalVaccine animalVaccine = repository.save(entity);

        nextApplicationDateService.create(animalVaccine, dto);
    }

    public Page<AnimalVaccine> findAll(AnimalVaccineCriteria criteria, Pageable pageable) {
        AnimalVaccineSpecification specification = new AnimalVaccineSpecification(criteria);
        Page<AnimalVaccine> animalVaccineLists = repository.findAll(specification, pageable);

        log.info("Fetched {} AnimalVaccines.", animalVaccineLists.getContent().size());
        return animalVaccineLists;
    }

    public AnimalVaccine findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource, AnimalVaccine.class, id));
    }

    public void deleteById(Long id) {
        findById(id);
        repository.deleteById(id);
        log.info("AnimalVaccine '{}' was successfully deleted.", id);
    }

    public void save(AnimalVaccine entity) {
        repository.save(entity);
    }

    public List<AnimalVaccine> findNextApplicationDates() {
        LocalDate now = LocalDate.now();
        LocalDate fourDaysFromNow = now.plusDays(4);
        return repository.findNextVaccinationDatesIn4DaysInterval(fourDaysFromNow);
    }
}
