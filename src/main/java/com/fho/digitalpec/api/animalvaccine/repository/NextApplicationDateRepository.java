package com.fho.digitalpec.api.animalvaccine.repository;

import java.time.LocalDate;
import java.util.List;

import com.fho.digitalpec.api.animalvaccine.entity.AnimalVaccine;
import com.fho.digitalpec.api.animalvaccine.entity.NextApplicationDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;


@Repository
public interface NextApplicationDateRepository extends JpaRepository<NextApplicationDate, Long> {

    List<NextApplicationDate> findByAnimalVaccine(AnimalVaccine entity);

    @Modifying
    void deleteByAnimalVaccineIdAndApplicationDateIn(Long entityId, List<LocalDate> localDatesToRemove);

    @Modifying
    void deleteAllByAnimalVaccineId(Long entityId);
}
