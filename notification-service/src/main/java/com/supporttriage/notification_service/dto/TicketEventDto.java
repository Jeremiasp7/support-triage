package com.supporttriage.notification_service.dto;

public record TicketEventDto(
    String ticketId,
    String requesterEmail,
    String status,
    String title,
    String proposedSolution
) {} 
