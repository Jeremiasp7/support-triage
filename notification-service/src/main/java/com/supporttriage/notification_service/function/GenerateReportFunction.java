package com.supporttriage.notification_service.function;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.supporttriage.notification_service.dto.TicketEventDto;
import com.supporttriage.notification_service.dto.TicketReportDto;

@Component
public class GenerateReportFunction implements Function<TicketEventDto, TicketReportDto> {
    
    private static final Logger log = LoggerFactory.getLogger(GenerateReportFunction.class);

    @Override
    public TicketReportDto apply(TicketEventDto event) {
        log.info("[Serverless] Gerando relatorio para ticket: {}", event.ticketId());

        String summary = String.format(
            "Chamado: %s | Status final: %s | %s",
            event.title(),
            event.status(),
            event.proposedSolution() != null
                    ? "Solução aplicada: " + event.proposedSolution()
                    : "Encaminhado para atendimento humano"
        );

        return new TicketReportDto(
            event.ticketId(),
            event.title(),
            event.status(),
            LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            summary
        );
    }

}
