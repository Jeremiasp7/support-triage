package com.supporttriage.ticket_service.dto;

// criado como record, mas isso pode mudar
public record TicketRequestDto(
    String title,
    String description,
    String requesterEmail
) {}
