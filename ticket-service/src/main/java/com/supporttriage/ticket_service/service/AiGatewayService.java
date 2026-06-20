package com.supporttriage.ticket_service.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.client.HttpSyncGraphQlClient;
import org.springframework.stereotype.Service;

import com.supporttriage.ticket_service.domain.Ticket;
import com.supporttriage.ticket_service.dto.AnalysisResultDto;
import com.supporttriage.ticket_service.exceptions.AiServiceUnavailableException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AiGatewayService {
    
    private static final Logger log = LoggerFactory.getLogger(AiGatewayService.class);

    private final HttpSyncGraphQlClient aiServiceGraphQlClient;

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
    
    public AnalysisResultDto analyzeTicket(Ticket ticket) {
        log.info("Solicitando análise de IA para o ticket: {}", ticket.getId());

        try {
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
        } catch (Exception ex) {
            log.error("Falha ao comunicar com ai-service para o ticket {}: {}", ticket.getId(), ex.getMessage());
            throw new AiServiceUnavailableException("Falha ao comunicar com ai-service", ex);
        }
    }
}
