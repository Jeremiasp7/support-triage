package com.supporttriage.ticket_service.client;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/graphql")
public interface AiServiceClient {
    
    @PostExchange
    Map<String, Object> analyzeTicket(@RequestBody Map<String, Object> graphQlPayload);
}
