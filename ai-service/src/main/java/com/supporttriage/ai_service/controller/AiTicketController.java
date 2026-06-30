package com.supporttriage.ai_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.supporttriage.ai_service.dto.AnalysisResultDto;
import com.supporttriage.ai_service.dto.TicketInputDto;
import com.supporttriage.ai_service.service.TicketAnalysisService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AiTicketController {
    
    private static final Logger log = LoggerFactory.getLogger(AiTicketController.class);

    private final TicketAnalysisService ticketAnalysisService;

    @MutationMapping
    public AnalysisResultDto analyzeTicket(@Argument("input") TicketInputDto input) {
        log.info("Requisição recebida de análise via GraphQL para o ticket: {}", input.ticketId());

        return ticketAnalysisService.analyze(input);
    }
}
