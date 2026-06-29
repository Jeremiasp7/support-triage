package com.supporttriage.ticket_service.client;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.supporttriage.ticket_service.dto.TicketEventDto;

@HttpExchange("/validateTicketFunction,notifyUserFunction")
public interface NotificationServiceClient {

    @PostExchange
    void sendNotification(@RequestBody TicketEventDto event);
}
