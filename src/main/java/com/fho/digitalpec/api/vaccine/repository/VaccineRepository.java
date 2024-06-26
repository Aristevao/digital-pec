package com.fho.digitalpec.api.vaccine.repository;

import java.util.Optional;

import com.fho.digitalpec.api.vaccine.entity.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long>, JpaSpecificationExecutor<Vaccine> {

    Optional<Vaccine> findByName(String name);
}
