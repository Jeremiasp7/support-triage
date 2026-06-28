package com.supporttriage.notification_service.dto;

public record TicketReportDto(
    String ticketId,
    String title,
    String status,
    String generatedAt,
    String summary
) {}
