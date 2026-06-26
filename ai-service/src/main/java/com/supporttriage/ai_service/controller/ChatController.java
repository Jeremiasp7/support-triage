package com.supporttriage.ai_service.controller;

import com.supporttriage.ai_service.service.ChatService;
import com.supporttriage.ai_service.service.ChatService.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private final ChatService chatService;

    @QueryMapping
    public ChatResponse chat(@Argument String sessionId, @Argument String message) {
        log.info("Query GraphQL 'chat' recebida - sessao: {}", sessionId);
        return chatService.chat(sessionId, message);
    }
}