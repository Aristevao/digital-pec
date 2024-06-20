package com.fho.digitalpec.api.animalvaccine.repository;

import java.time.LocalDate;
import java.util.List;

import com.fho.digitalpec.api.animalvaccine.entity.AnimalVaccine;
import com.fho.digitalpec.api.animalvaccine.entity.NextApplicationDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface NextApplicationDateRepository extends JpaRepository<NextApplicationDate, Long> {
    List<NextApplicationDate> findByAnimalVaccine(AnimalVaccine entity);

    void deleteByAnimalVaccineIdAndApplicationDateIn(Long entityId, List<LocalDate> localDatesToRemove);

    @Query(value = "SELECT nad.* " +
            "FROM digital_pec.next_application_date nad " +
            "JOIN digital_pec.animal_vaccine av " +
            "ON av.id = nad.animal_vaccine_id " +
            "WHERE nad.application_date <= NOW() + interval '4 days' " +
            "AND av.completed = FALSE " +
            "ORDER BY nad.application_date DESC", nativeQuery = true)
    List<NextApplicationDate> findNextVaccinationDatesIn4DaysInterval();
}
