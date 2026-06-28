package com.supporttriage.notification_service.function;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.supporttriage.notification_service.dto.TicketEventDto;

@Component
public class ValidateTicketFunction implements Function<TicketEventDto, TicketEventDto>{

    private static final Logger log = LoggerFactory.getLogger(ValidateTicketFunction.class);

    @Override
    public TicketEventDto apply(TicketEventDto event) {
        log.info("[Serverless] Validando ticket: {}", event.ticketId());

        if (event.ticketId() == null || event.ticketId().isBlank()) {
            throw new IllegalArgumentException("ticketId e obrigatorio");
        }
        if (event.title() == null || event.title().isBlank()) {
            throw new IllegalArgumentException("title e obrigatorio");
        }

        return new TicketEventDto(
                event.ticketId().trim(),
                event.requesterEmail() != null
                        ? event.requesterEmail().toLowerCase().trim()
                        : null,
                event.status(),
                event.title().trim(),
                event.proposedSolution()
        );
    }
    
}
