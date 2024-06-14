package com.fho.digitalpec.api.animal.repository;

import com.fho.digitalpec.api.animal.dto.AnimalCriteria;
import com.fho.digitalpec.api.animal.entity.Animal;
import com.fho.digitalpec.api.unit.entity.Unit;
import com.fho.digitalpec.api.user.entity.User;
import com.fho.digitalpec.security.authentication.LoggedUser;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AnimalSpecification implements Specification<Animal> {

    private AnimalCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Animal> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        Long loggedUserId = LoggedUser.getLoggedInUser().getId();
        Join<Animal, User> userJoin = root.join("user");
        predicate = criteriaBuilder.and(predicate,
                criteriaBuilder.equal(userJoin.get("id"), loggedUserId));

        if (criteria.getName() != null && !criteria.getName().isEmpty()) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + criteria.getName().toLowerCase() + "%"));
        }
        if (criteria.getIdentification() != null && !criteria.getIdentification().isEmpty()) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("identification"), criteria.getIdentification()));
        }
        if (criteria.getBreed() != null && !criteria.getBreed().isEmpty()) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("breed")), "%" + criteria.getBreed().toLowerCase() + "%"));
        }
        if (criteria.getSex() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("sex"), criteria.getSex()));
        }
        if (criteria.getBirthdate() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("birthdate"), criteria.getBirthdate()));
        }
        if (criteria.getRegistrationDate() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("registrationDate"), criteria.getRegistrationDate()));
        }
        if (criteria.getDescription() != null && !criteria.getDescription().isEmpty()) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + criteria.getDescription().toLowerCase() + "%"));
        }
        if (criteria.getUnitId() != null) {
            Join<Animal, Unit> unitJoin = root.join("unit");
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(unitJoin.get("id"), criteria.getUnitId()));
        }

        return predicate;
    }
}
