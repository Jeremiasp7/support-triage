package com.supporttriage.ticket_service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.supporttriage.ticket_service.domain.TicketCategory;
import com.supporttriage.ticket_service.domain.TicketPriority;
import com.supporttriage.ticket_service.domain.TicketStatus;

import lombok.Data;

@Data
public class TicketResponseDto {
    private UUID id;
    private String title;
    private String description;
    private TicketStatus status;
    private TicketPriority priority;
    private TicketCategory category;
    private String requesterEmail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
