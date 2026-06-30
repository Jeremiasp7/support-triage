package com.supporttriage.ai_service.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
    
    private static final Logger log = LoggerFactory.getLogger(ChatService.class);

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    private static final String PROMPT = """
            Você é um assistente de suporte técnico especializado.
            Seu objetivo é ajudar o usuário a resolver problemas técnicos de forma clara e objetiva.
            Use o contexto da base de conhecimento fornecido para embasar suas respostas.
            Mantenha um tom profissional e empático.
            Se não souber a resposta ou o problema for muito complexo, oriente o usuário a
            aguardar atendimento humano.
            Responda sempre em português.
            """;
    
    public record ChatResponse(String reply, String sessionId, Boolean resolvedAutomatically) {}

    private final List<ToolCallback> registeredTools;

    @PostConstruct
        public void listAllAvailableTools() {
            log.info("--- Ferramentas MCP Registradas no Sistema ---");
            for (ToolCallback tool : registeredTools) {
                log.info("Ferramenta disponível: {}", tool.getClass());
            }
        }

    public ChatResponse chat(String sessionId, String message) {
        log.info("Chat recebido - sessao: {}, mensagem: {}", sessionId, message);

        List<Document> relevantDocs = vectorStore.similaritySearch(
            SearchRequest.builder()
                .query(message)
                .topK(1)
                .similarityThreshold(0.4)
                .build()
        );

        String context = relevantDocs.isEmpty()
            ? ""
            : "\n\nBase de conhecimento relevante:\n" + relevantDocs.stream()
                .map(Document::getText)
                .reduce("", (a, b) -> a + "\n---\n" + b);
        
        String reply = chatClient.prompt()
            .system(PROMPT + context)
            .user(message)
            .advisors(advisor -> advisor.param(
                ChatMemory.CONVERSATION_ID, sessionId))
            .tools("getTicketHistory", "escalateTicket")
            .call()
            .content();
        
        log.info("Resposta gerada para a sessao {}: {}", sessionId, reply);

        boolean resolvedAutomatically = reply.toLowerCase().contains("resolvido")
                || reply.toLowerCase().contains("problema solucionado")
                || reply.toLowerCase().contains("tente agora");
        
        return new ChatResponse(reply, sessionId, resolvedAutomatically);     
    }
}