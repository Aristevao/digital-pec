package com.fho.digitalpec.api.notification.service;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import java.util.List;

import com.fho.digitalpec.api.notification.entity.Notification;
import com.fho.digitalpec.api.notification.repository.NotificationRepository;
import com.fho.digitalpec.exception.ResourceNotFoundException;
import com.fho.digitalpec.security.authentication.LoggedUser;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final MessageSource messageSource;
    private final NotificationRepository repository;

    public void create(Notification entity) {
        entity.setIsRead(FALSE);
        repository.save(entity);
    }

    public Page<Notification> findAll(Pageable pageable) {
        Long loggedUserId = LoggedUser.getLoggedInUser().getId();
        Page<Notification> notifications = repository.findByUserId(loggedUserId, pageable);
        log.info("Fetched {} Notifications.", notifications.getContent().size());
        return notifications;
    }

    public Notification findById(Long id) {
        Long loggedUserId = LoggedUser.getLoggedInUser().getId();
        Notification notification = repository.findByIdAndUserId(id, loggedUserId);

        if (notification == null) {
            throw new ResourceNotFoundException(messageSource, Notification.class, id);
        }

        return notification;
    }

    public void markAsRead(Long id) {
        Notification notification = findById(id);
        notification.setIsRead(TRUE);

        repository.save(notification);
    }

    @Transactional
    public void markAllAsRead() {
        Long loggedUserId = LoggedUser.getLoggedInUser().getId();

        List<Notification> notifications = repository.findByUserId(loggedUserId);
        notifications.forEach(notification -> notification.setIsRead(TRUE));

        repository.saveAll(notifications);
    }
}
