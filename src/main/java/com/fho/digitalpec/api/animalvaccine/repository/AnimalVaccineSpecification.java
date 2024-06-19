package com.fho.digitalpec.api.animalvaccine.repository;

import java.time.LocalDate;

import com.fho.digitalpec.api.animalvaccine.dto.AnimalVaccineCriteria;
import com.fho.digitalpec.api.animalvaccine.entity.AnimalVaccine;
import com.fho.digitalpec.api.animalvaccine.entity.NextApplicationDate;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AnimalVaccineSpecification implements Specification<AnimalVaccine> {

    private AnimalVaccineCriteria criteria;

    @Override
    public Predicate toPredicate(Root<AnimalVaccine> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (criteria.getAnimal() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("animal"), criteria.getAnimal()));
        }
        if (criteria.getVaccine() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("vaccine"), criteria.getVaccine()));
        }
        if (criteria.getCompleted() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("completed"), criteria.getCompleted()));
        }
        if (criteria.getApplicationDate() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("applicationDate"), criteria.getApplicationDate()));
        }
        if (criteria.getNextApplicationDates() != null && !criteria.getNextApplicationDates().isEmpty()) {
            Join<AnimalVaccine, NextApplicationDate> nextApplicationDateJoin = root.join("nextApplicationDates");
            CriteriaBuilder.In<LocalDate> inClause = criteriaBuilder.in(nextApplicationDateJoin.get("applicationDate"));
            for (NextApplicationDate nextDate : criteria.getNextApplicationDates()) {
                inClause.value(nextDate.getApplicationDate());
            }
            predicate = criteriaBuilder.and(predicate, inClause);
        }

        return predicate;
    }
}
