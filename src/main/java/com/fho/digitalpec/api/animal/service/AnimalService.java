package com.fho.digitalpec.api.animal.service;

import static java.lang.String.format;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.fho.digitalpec.api.animal.dto.AnimalCriteria;
import com.fho.digitalpec.api.animal.entity.Animal;
import com.fho.digitalpec.api.animal.repository.AnimalRepository;
import com.fho.digitalpec.api.animal.repository.AnimalSpecification;
import com.fho.digitalpec.api.dashboard.dto.AnimalEvolutionProjection;
import com.fho.digitalpec.api.dashboard.dto.AnimalGrowthDTO;
import com.fho.digitalpec.api.specie.service.SpecieService;
import com.fho.digitalpec.api.unit.service.UnitService;
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
    private final UnitService unitService;

    @Transactional
    public void create(Animal entity) {
        validateIdentificationUniqueness(entity, null);

        unitService.findById(entity.getUnit().getId());

        Long loggedUserId = LoggedUser.getLoggedInUserId();
        entity.setUser(userService.findById(loggedUserId));

        specieService.create(entity);

        repository.save(entity);

        // TODO: Upload picture to S3
        // if (Objects.nonNull(dto.getPicture())) {
        // animalFileStorage.uploadImage(animal.getId(), dto.getPicture());
        // }
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

    public Integer countAnimalsByUnit(Long unitId) {
        return repository.countAnimalsByUnitId(unitId);
    }

    public Integer countAll() {
        return repository.countAnimalsByUserId(LoggedUser.getLoggedInUserId());
    }

    public Map<String, Long> countBySpecies() {
        List<Object[]> results = repository.countAnimalsBySpecies();

        return results.stream()
                .collect(Collectors.toMap(
                        result -> (String) result[0], // Specie
                        result -> (Long) result[1] // Count
                ));
    }

    public void deleteById(Long id) {
        findById(id);
        repository.deleteById(id);
        log.info("Animal '{}' was successfully deleted.", id);
    }

    public List<AnimalGrowthDTO> getAnimalGrowthByPeriod(LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = repository.countAnimalsByRegistrationDate(startDate, endDate);

        Map<LocalDate, Map<String, Long>> growthData = new HashMap<>();

        for (Object[] result : results) {
            LocalDate registrationDate = (LocalDate) result[0];
            String specie = (String) result[1];
            Long total = (Long) result[2];

            growthData.computeIfAbsent(registrationDate, k -> new HashMap<>())
                    .put(specie, total);
        }

        List<AnimalGrowthDTO> growthDTOList = new ArrayList<>();

        for (Map.Entry<LocalDate, Map<String, Long>> entry : growthData.entrySet()) {
            LocalDate date = entry.getKey();
            Map<String, Long> speciesCount = entry.getValue();
            growthDTOList.add(new AnimalGrowthDTO(date, speciesCount));
        }

        return growthDTOList;
    }

    public List<Map<String, Object>> getAnimalEntries() {
        List<AnimalEvolutionProjection> rawData = repository.findAnimalEntriesByUserId(LoggedUser.getLoggedInUserId());

        Map<String, Map<String, Long>> monthlyData = new TreeMap<>(); // Garantir ordenação por mês
        Map<String, Long> accumulatedTotals = new HashMap<>();

        for (AnimalEvolutionProjection data : rawData) {
            String monthYear = String.format("%02d-%d", data.getMonth(), data.getYear());
            monthlyData.putIfAbsent(monthYear, new HashMap<>());

            Map<String, Long> speciesData = monthlyData.get(monthYear);
            speciesData.put(data.getSpecie(), speciesData.getOrDefault(data.getSpecie(), 0L) + data.getCount());

            accumulatedTotals.put(monthYear, accumulatedTotals.getOrDefault(monthYear, 0L) + data.getCount());
        }

        // Construir a lista de resposta
        List<Map<String, Object>> result = new ArrayList<>();
        long cumulativeTotal = 0;

        for (Map.Entry<String, Map<String, Long>> entry : monthlyData.entrySet()) {
            String monthYear = entry.getKey();
            Map<String, Long> speciesData = entry.getValue();

            Map<String, Object> responseEntry = new LinkedHashMap<>();
            responseEntry.put("month", monthYear);
            responseEntry.put("total", cumulativeTotal += accumulatedTotals.get(monthYear));

            responseEntry.putAll(speciesData);

            result.add(responseEntry);
        }

        return result;
    }

    private void validateIdentificationUniqueness(Animal entity, Long id) {
        repository.findByIdentification(entity.getIdentification())
                .ifPresent(animal -> {
                    if (!animal.getId().equals(id)) {
                        throw new ConflictException(ErrorCode.DUPLICATED_ANIMAL_ID,
                                format("An animal with the same identification already exists: '%s'.",
                                        entity.getIdentification()));
                    }
                });
    }

    public List<Map<String, Object>> getAnimalEvolution() {
        List<Object[]> results = repository.findAnimalEvolutionByUserId(LoggedUser.getLoggedInUserId());

        // Structures to accumulate data
        Map<String, Map<String, Long>> cumulativeData = new LinkedHashMap<>();
        Map<String, Long> totalPerSpecie = new HashMap<>();
        Set<String> allSpecies = new HashSet<>();

        // Iterate over results and build cumulative data
        for (Object[] result : results) {
            int year = ((Number) result[0]).intValue();
            int month = ((Number) result[1]).intValue();
            String specie = (String) result[2];
            long count = ((Number) result[3]).longValue();

            String monthKey = String.format("%02d-%d", month, year);

            // Add to the set of known species
            allSpecies.add(specie);

            // Initialize the month if it doesn't exist
            cumulativeData.putIfAbsent(monthKey, new HashMap<>());
            Map<String, Long> monthData = cumulativeData.get(monthKey);

            // Update cumulative count
            totalPerSpecie.put(specie, totalPerSpecie.getOrDefault(specie, 0L) + count);
            monthData.put(specie, totalPerSpecie.get(specie));
        }

        // Fill in missing months for each species
        List<String> sortedMonths = new ArrayList<>(cumulativeData.keySet());
        sortedMonths.sort(String::compareTo);

        for (int i = 1; i < sortedMonths.size(); i++) {
            String currentMonth = sortedMonths.get(i);
            String previousMonth = sortedMonths.get(i - 1);

            Map<String, Long> currentData = cumulativeData.get(currentMonth);
            Map<String, Long> previousData = cumulativeData.get(previousMonth);

            for (String specie : allSpecies) {
                // If the species does not exist in the current month, copy from the previous month
                currentData.putIfAbsent(specie, previousData.getOrDefault(specie, 0L));
            }
        }

        // Convert to the return structure
        List<Map<String, Object>> response = new ArrayList<>();
        cumulativeData.forEach((month, data) -> {
            Map<String, Object> monthEntry = new LinkedHashMap<>();
            monthEntry.put("month", month);
            monthEntry.put("total", data.values().stream().mapToLong(Long::longValue).sum());
            monthEntry.putAll(data);
            response.add(monthEntry);
        });

        return response;
    }
}
