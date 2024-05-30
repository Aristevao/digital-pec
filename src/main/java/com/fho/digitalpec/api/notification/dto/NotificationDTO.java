package com.fho.digitalpec.api.notification.dto;

import java.time.LocalDateTime;

import com.fho.digitalpec.api.user.entity.User;

import lombok.Data;

@Data
public class NotificationDTO {

    private Long id;
    private String title;
    private String message;
    private Boolean isRead;
    private User user;
    private LocalDateTime createdAt;
}
