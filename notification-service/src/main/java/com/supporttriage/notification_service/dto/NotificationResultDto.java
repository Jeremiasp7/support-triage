package com.supporttriage.notification_service.dto;

public record NotificationResultDto(
    String ticketId,
    String message,
    Boolean success
) {}
