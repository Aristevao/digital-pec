package com.fho.digitalpec.api.notification.controller;

import com.fho.digitalpec.api.notification.dto.NotificationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Notification API", description = "API for managing notifications")
public interface NotificationApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new notification", description = "Creates a new notification with the provided data")
    void create(@Valid @RequestBody NotificationDTO dto);

    @GetMapping
    @Operation(summary = "Find all notifications", description = "Returns a paginated list of all notifications")
    Page<NotificationDTO> findAll(@RequestParam(required = false) Boolean isRead, Pageable pageable);

    @GetMapping("{id}")
    @Operation(summary = "Find a notification by ID", description = "Returns the details of a notification by its ID")
    NotificationDTO findById(@Parameter(description = "ID of the notification to be fetched") @PathVariable Long id);

    @PatchMapping("{id}/markAsRead")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Mark a notification as read", description = "Marks a notification as read by its ID")
    void markAsRead(@Parameter(description = "ID of the notification to be marked as read") @PathVariable Long id);

    @PatchMapping("markAllAsRead")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Mark all notifications as read", description = "Marks all notifications as read for the current user")
    void markAllAsRead();

    @PostMapping("job")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Manually run job", description = "Runs job to send vaccine application reminder notifications.")
    void sendReminderNotification();
}
