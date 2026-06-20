package com.supporttriage.ticket_service.resilience;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.supporttriage.ticket_service.dto.AnalysisResultDto;

@Component
public class AiServiceFallback {
    
    private static final Logger log = LoggerFactory.getLogger(AiServiceFallback.class);

    public AnalysisResultDto fallback(String ticketId, Throwable cause) {
        log.warn("[Fallback] ai-service indisponível para o ticket {}. Causa: {}. Escalando para humano.", ticketId, cause.getMessage());

        return new AnalysisResultDto(
            "OTHER",
            "MEDIUM",
            "Não foi possível analisar automaticamente. Chamado escalado para atendimento humano.",
            0.0,
            true,
            List.of()
        );
    }
}
