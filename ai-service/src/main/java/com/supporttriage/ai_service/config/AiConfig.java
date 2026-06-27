package com.supporttriage.ai_service.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.supporttriage.ai_service.mcp.EscalationTool;
import com.supporttriage.ai_service.mcp.TicketHistoryTool;

@Configuration
public class AiConfig {

    @Bean
    ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(new InMemoryChatMemoryRepository())
                .build();
    }

    @Bean
    ToolCallbackProvider toolCallbackProvider(
            TicketHistoryTool ticketHistoryTool,
            EscalationTool escalationTool) {

        return MethodToolCallbackProvider.builder()
                .toolObjects(ticketHistoryTool, escalationTool)
                .build();
    }

    @Bean
    ChatClient chatClient(
            ChatClient.Builder builder,
            ChatMemory chatMemory,
            TicketHistoryTool ticketHistoryTool,
            EscalationTool escalationTool) {

        return builder
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                )
                .defaultTools(ticketHistoryTool, escalationTool)
                .build();
    }
}
