package com.fho.digitalpec.api.animal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fho.digitalpec.api.animal.entity.AnimalControl;

@Repository
public interface AnimalControlRepository extends JpaRepository<AnimalControl, Long> {

    Optional<AnimalControl> findByUserId(Long userId);
}
