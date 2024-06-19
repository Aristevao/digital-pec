package com.fho.digitalpec.api.vaccine.repository;

import com.fho.digitalpec.api.vaccine.dto.VaccineCriteria;
import com.fho.digitalpec.api.vaccine.entity.Vaccine;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VaccineSpecification implements Specification<Vaccine> {

    private VaccineCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Vaccine> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (criteria.getName() != null && !criteria.getName().isEmpty()) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + criteria.getName().toLowerCase() + "%"));
        }
        if (criteria.getDescription() != null && !criteria.getDescription().isEmpty()) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + criteria.getDescription().toLowerCase() + "%"));
        }

        return predicate;
    }
}
