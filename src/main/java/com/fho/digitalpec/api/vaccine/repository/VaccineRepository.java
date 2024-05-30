package com.fho.digitalpec.api.vaccine.repository;

import com.fho.digitalpec.api.vaccine.entity.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long> {
}
