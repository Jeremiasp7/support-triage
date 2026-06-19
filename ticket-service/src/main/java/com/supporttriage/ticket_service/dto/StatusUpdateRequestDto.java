package com.supporttriage.ticket_service.dto;

import com.supporttriage.ticket_service.domain.TicketStatus;

import jakarta.validation.constraints.NotNull;

public record StatusUpdateRequestDto(
    @NotNull(message = "Status é obrigatório")
    TicketStatus status
) {}
