package com.supporttriage.ticket_service.exceptions;

import java.util.UUID;

public class TicketNotFoundException extends RuntimeException {
    
    public TicketNotFoundException(UUID ticketId) {
        super("Ticket não encontrado: " +ticketId);
    }
}
