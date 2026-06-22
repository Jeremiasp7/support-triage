package com.supporttriage.ai_service.dto;

public record TicketInputDto(
    String ticketId,
    String title,
    String description
) {
    public record SourceDto(String documentTitle, String excerpt) {}
}
