package com.fho.digitalpec.api.specie.repository;

import java.util.List;

import com.fho.digitalpec.api.specie.entity.Specie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecieRepository extends JpaRepository<Specie, Long> {
    Specie findByName(String name);

    List<Specie> findAllByOrderByNameAsc();
}
