package com.fho.digitalpec.api.vaccine.repository;

import java.util.List;

import com.fho.digitalpec.api.vaccine.entity.VaccineSpecie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineSpecieRepository extends JpaRepository<VaccineSpecie, Long> {

    List<VaccineSpecie> findByVaccineIdIn(List<Long> vaccineIds);
}
