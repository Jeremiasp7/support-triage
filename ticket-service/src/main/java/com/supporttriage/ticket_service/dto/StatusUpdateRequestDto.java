package com.supporttriage.ticket_service.dto;

import com.supporttriage.ticket_service.domain.TicketStatus;

public record StatusUpdateRequestDto(
    TicketStatus status
) {}
