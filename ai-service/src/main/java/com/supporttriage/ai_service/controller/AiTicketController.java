package com.supporttriage.ai_service.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.supporttriage.ai_service.dto.AnalysisResultDto;
import com.supporttriage.ai_service.dto.TicketInputDto;

@Controller
public class AiTicketController {
    
    private static final Logger log = LoggerFactory.getLogger(AiTicketController.class);

    @SuppressWarnings("unused")
    @MutationMapping
    public AnalysisResultDto analyzeTicket(@Argument("input") TicketInputDto input) {
        log.info("Requisição recebida de análise mockada via GraphQL para o ticket: {}", input.ticketId());

        if (true) throw new RuntimeException("Simulando falha do LLM para testar Circuit Breaker");

        return new AnalysisResultDto(
            "NETWORK",
            "HIGH",
            "Reinicie os equipamentos de rede e verifique os logs do firewall.",
            0.95,
            false,
            List.of()
        );
    }
}
