package com.fho.digitalpec.api.notification.repository;

import com.fho.digitalpec.api.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
