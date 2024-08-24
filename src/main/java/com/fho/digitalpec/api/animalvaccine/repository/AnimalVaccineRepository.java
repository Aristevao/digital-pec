package com.fho.digitalpec.api.animalvaccine.repository;

import java.time.LocalDate;
import java.util.List;

import com.fho.digitalpec.api.animalvaccine.entity.AnimalVaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AnimalVaccineRepository extends JpaRepository<AnimalVaccine, Long>, JpaSpecificationExecutor<AnimalVaccine> {

    @Query("SELECT DISTINCT av " +
            "FROM AnimalVaccine av " +
            "JOIN av.nextApplicationDates nad " +
            "WHERE nad.applicationDate <= :fourDaysFromNow AND " +
            "av.completed = false")
    List<AnimalVaccine> findNextVaccinationDatesIn4DaysInterval(LocalDate fourDaysFromNow);

    List<AnimalVaccine> findByVaccineId(Long vaccineId);
}
