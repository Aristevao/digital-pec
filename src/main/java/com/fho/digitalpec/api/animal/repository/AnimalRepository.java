package com.fho.digitalpec.api.animal.repository;

import java.util.List;
import java.util.Optional;

import com.fho.digitalpec.api.animal.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    Optional<Animal> findByIdentification(String identification);

    List<Animal> findAllByUserIdOrderByNameAsc(Long userId);

    Optional<Animal> findByIdAndUserId(Long id, Long userId);
}
