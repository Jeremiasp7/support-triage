package com.supporttriage.ticket_service.dto;

import java.util.List;

public record AnalysisResultDto(
    String suggestedCategory,
    String suggestedPriority,
    String proposedSolution,
    Double confidence,
    Boolean requiresHuman,
    List<SourceDto> sources
) {
    public record SourceDto(String documentTitle, String excerpt) {}
}
