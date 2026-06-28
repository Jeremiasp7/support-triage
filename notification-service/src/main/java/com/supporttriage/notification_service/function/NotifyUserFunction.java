package com.supporttriage.notification_service.function;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.supporttriage.notification_service.dto.NotificationResultDto;
import com.supporttriage.notification_service.dto.TicketEventDto;

@Component
public class NotifyUserFunction implements Function<TicketEventDto, NotificationResultDto>{
    
    private static final Logger log = LoggerFactory.getLogger(NotifyUserFunction.class);

    @Override
    public NotificationResultDto apply(TicketEventDto event) {
        log.info("[Serverless] Notificando {} sobre ticket {} - status: {}",
                event.requesterEmail(), event.ticketId(), event.status());
        
        String message = buildMessage(event);
        log.info("[Serveless] Mensagem gerada: {}", message);

        return new NotificationResultDto(
            event.ticketId(),
            "Notificação enviada para " + event.requesterEmail() + ": " + message,
            true
        );
    }

    private String buildMessage(TicketEventDto event) {
        return switch (event.status()) {
            case "AUTO_RESOLVED" -> String.format(
                    "Seu chamado '%s' foi resolvido automaticamente. Solucao: %s",
                    event.title(), event.proposedSolution());
            case "ESCALATED" -> String.format(
                    "Seu chamado '%s' foi encaminhado para atendimento humano. " +
                    "Um tecnico entrara em contato em breve.",
                    event.title());
            case "CLOSED" -> String.format(
                    "Seu chamado '%s' foi encerrado. Obrigado pelo contato.",
                    event.title());
            default -> String.format(
                    "Seu chamado '%s' foi atualizado para o status: %s",
                    event.title(), event.status());
        };
    }
}
