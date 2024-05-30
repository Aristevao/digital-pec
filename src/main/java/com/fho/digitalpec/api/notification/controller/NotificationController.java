package com.fho.digitalpec.api.notification.controller;


import com.fho.digitalpec.api.notification.dto.NotificationDTO;
import com.fho.digitalpec.api.notification.mapper.NotificationMapper;
import com.fho.digitalpec.api.notification.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("notification")
public class NotificationController {

    private final NotificationService service;
    private final NotificationMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody NotificationDTO dto) {
        log.info("Creating notification. Payload: {}.", dto);
        service.create(mapper.toEntity(dto));
    }
}
