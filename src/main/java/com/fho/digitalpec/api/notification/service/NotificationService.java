package com.fho.digitalpec.api.notification.service;

import java.util.List;

import com.fho.digitalpec.api.notification.entity.Notification;
import com.fho.digitalpec.api.notification.repository.NotificationRepository;
import com.fho.digitalpec.exception.ResourceNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final MessageSource messageSource;
    private final NotificationRepository repository;

    public void create(Notification entity) {
        repository.save(entity);
    }

    public Page<Notification> findAll(Pageable pageable) {
        Page<Notification> notifications = repository.findAll(pageable);
        log.info("Fetched {} Notifications.", notifications.getContent().size());
        return notifications;
    }

    public Notification findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource, Notification.class, id));
    }

    public void markAsRead(Long id) {
        Long loggedUserId = 1L;
        Notification notification = repository.findByIdAndUserId(id, loggedUserId);
        notification.setIsRead(Boolean.TRUE);
        create(notification);
    }

    public void markAllAsRead() {
        Long loggedUserId = 1L;

        List<Notification> notifications = repository.findByUserId(loggedUserId);
        notifications.forEach(notification -> notification.setIsRead(Boolean.TRUE));

        repository.saveAll(notifications);
    }
}
