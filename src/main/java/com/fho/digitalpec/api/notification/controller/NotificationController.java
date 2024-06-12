package com.fho.digitalpec.api.notification.controller;


import com.fho.digitalpec.api.notification.dto.NotificationDTO;
import com.fho.digitalpec.api.notification.entity.Notification;
import com.fho.digitalpec.api.notification.mapper.NotificationMapper;
import com.fho.digitalpec.api.notification.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("notification")
public class NotificationController implements NotificationApi {

    private final NotificationService service;
    private final NotificationMapper mapper;

    @Override
    public void create(@Valid @RequestBody NotificationDTO dto) {
        log.info("Creating notification. Payload: '{}'.", dto);
        service.create(mapper.toEntity(dto));
    }

    @Override
    public Page<NotificationDTO> findAll(Pageable pageable) {
        log.info("Finding all notifications.");
        return service.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public NotificationDTO findById(@PathVariable Long id) {
        log.info("Getting notification with id: '{}'.", id);
        Notification notification = service.findById(id);
        return mapper.toDto(notification);
    }

    @Override
    public void markAsRead(@PathVariable Long id) {
        log.info("Marking notification with id: '{}' as read.", id);
        service.markAsRead(id);
    }

    @Override
    public void markAllAsRead() {
        log.info("Marking all notifications as read.");
        service.markAllAsRead();
    }
}
