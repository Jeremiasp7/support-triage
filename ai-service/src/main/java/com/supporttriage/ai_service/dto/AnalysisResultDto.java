package com.supporttriage.ai_service.dto;

import java.util.List;

import com.supporttriage.ai_service.dto.TicketInputDto.SourceDto;

public record AnalysisResultDto(
    String suggestedCategory,
    String suggestedPriority,
    String proposedSolution,
    Double confidence,
    Boolean requiresHuman,
    List<SourceDto> sources
) {}
