package com.fho.digitalpec.api.notification.repository;

import com.fho.digitalpec.api.notification.entity.Notification;
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
public class NotificationSpecification implements Specification<Notification> {

    private Boolean isRead;

    @Override
    public Predicate toPredicate(Root<Notification> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        Long loggedUserId = LoggedUser.getLoggedInUserId();
        Join<Notification, User> userJoin = root.join("user");
        predicate = criteriaBuilder.and(predicate,
                criteriaBuilder.equal(userJoin.get("id"), loggedUserId));

        if (isRead != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("isRead"), isRead));
        }

        return predicate;
    }
}
