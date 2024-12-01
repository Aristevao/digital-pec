package com.fho.digitalpec.api.animal.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.fho.digitalpec.api.animal.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long>, JpaSpecificationExecutor<Animal> {
    Optional<Animal> findByIdentification(String identification);

    List<Animal> findAllByUserIdOrderByNameAsc(Long userId);

    Optional<Animal> findByIdAndUserId(Long id, Long userId);

    Integer countAnimalsByUnitId(Long unitId);

    Integer countAnimalsByUserId(Long userId);

    @Query("SELECT a.specie, COUNT(a) FROM Animal a GROUP BY a.specie")
    List<Object[]> countAnimalsBySpecies();

    List<Animal> findByUserId(Long userId);

    @Query("SELECT a.registrationDate AS registrationDate, a.specie AS specie, COUNT(a) AS total " +
            "FROM Animal a " +
            "WHERE a.registrationDate BETWEEN :startDate AND :endDate " +
            "GROUP BY a.registrationDate, a.specie " +
            "ORDER BY a.registrationDate ASC")
    List<Object[]> countAnimalsByRegistrationDate(LocalDate startDate, LocalDate endDate);
}
