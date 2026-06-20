package com.supporttriage.ticket_service.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.client.HttpSyncGraphQlClient;
import org.springframework.stereotype.Service;

import com.supporttriage.ticket_service.domain.Ticket;
import com.supporttriage.ticket_service.dto.AnalysisResultDto;
import com.supporttriage.ticket_service.resilience.AiServiceFallback;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AiGatewayService {
    
    private static final Logger log = LoggerFactory.getLogger(AiGatewayService.class);

    private final HttpSyncGraphQlClient aiServiceGraphQlClient;
    private final AiServiceFallback aiServiceFallback;

    private static final String ANALYZE_TICKET_MUTATION = """
            mutation AnalyzeTicket($input: TicketInput!) {
                analyzeTicket(input: $input) {
                    suggestedCategory
                    suggestedPriority
                    proposedSolution
                    confidence
                    requiresHuman
                    sources {
                        documentTitle
                        exerpt
                    }
                }
            }
            """;
    
    @Bulkhead(name = "ai-service")
    @CircuitBreaker(name = "ai-service", fallbackMethod = "analyzeFallback")
    @Retry(name = "ai-service")
    public AnalysisResultDto analyzeTicket(Ticket ticket) {
        log.info("Solicitando análise de IA para o ticket: {}", ticket.getId());

        Map<String, Object> input = Map.of(
            "ticketId", ticket.getId().toString(),
            "title", ticket.getTitle(),
            "description", ticket.getDescription()
        );

        AnalysisResultDto result = aiServiceGraphQlClient.document(ANALYZE_TICKET_MUTATION)
            .variable("input", input)
            .retrieveSync("analyzeTicket")
            .toEntity(AnalysisResultDto.class);

        log.info(
            "Análise recebida para o ticket {}: categoria = {}, prioridade = {}, requiresHuman = {}", 
            ticket.getId(), result.suggestedCategory(), result.suggestedPriority(), result.requiresHuman()
        );

        return result;
    }

    @SuppressWarnings("unused")
    private AnalysisResultDto analyzeFallback(Ticket ticket, Throwable cause) {
        log.error("Falha ao comunicar com ai-service para o ticket {}: {}", ticket.getId(), cause.getMessage());
        return aiServiceFallback.fallback(ticket.getId().toString(), cause);
    }
}
