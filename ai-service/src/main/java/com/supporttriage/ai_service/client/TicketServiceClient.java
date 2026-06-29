package com.supporttriage.ai_service.client;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PutExchange;
import com.supporttriage.ai_service.dto.StatusUpdateRequestDto;

@HttpExchange("api/v1/tickets")
public interface TicketServiceClient {
    
    @PutExchange("/{id}/status")
    void updateTicketStatus(@PathVariable("id") String ticketId, @RequestBody StatusUpdateRequestDto request);
}
