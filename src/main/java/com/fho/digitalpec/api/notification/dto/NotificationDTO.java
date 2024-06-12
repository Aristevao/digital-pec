package com.fho.digitalpec.api.notification.dto;

import java.time.LocalDateTime;

import com.fho.digitalpec.utils.mapper.SimpleDTO;

import lombok.Data;

@Data
public class NotificationDTO {

    private Long id;
    private String title;
    private String message;
    private Boolean isRead;
    private SimpleDTO user;
    private LocalDateTime createdAt;
}
