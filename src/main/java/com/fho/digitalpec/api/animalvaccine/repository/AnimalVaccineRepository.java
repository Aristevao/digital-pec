package com.fho.digitalpec.api.animalvaccine.repository;

import com.fho.digitalpec.api.animalvaccine.entity.AnimalVaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface AnimalVaccineRepository extends JpaRepository<AnimalVaccine, Long>, JpaSpecificationExecutor<AnimalVaccine> {
}
