package com.fho.digitalpec.api.notification.service;

import com.fho.digitalpec.api.notification.entity.Notification;
import com.fho.digitalpec.api.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;

    public void create(Notification entity) {
        repository.save(entity);
    }
}
