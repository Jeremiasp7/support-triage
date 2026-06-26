package com.supporttriage.ai_service.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import com.supporttriage.ai_service.dto.AnalysisResultDto;
import com.supporttriage.ai_service.dto.TicketInputDto;
import com.supporttriage.ai_service.dto.TicketInputDto.SourceDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketAnalysisService {
    
    private static final Logger log = LoggerFactory.getLogger(TicketAnalysisService.class);

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    private static final String PROMPT = """
            Você é um assistente especializado em triagem de chamados de suporte técnico.
            Sua tarefa é analisar o chamado fornecido e retornar uma resposta OBRIGATORIAMENTE
            no seguinte formato JSON, sem nenhum texto adicional antes ou depois:

            {
              "suggestedCategory": "<NETWORK|SOFTWARE|HARDWARE|ACCESS|OTHER>",
              "suggestedPriority": "<LOW|MEDIUM|HIGH|CRITICAL>",
              "proposedSolution": "<solução detalhada em português>",
              "confidence": <número entre 0.0 e 1.0>,
              "requiresHuman": <true|false>
            }

            Regras:
            - suggestedCategory deve ser exatamente um dos valores: NETWORK, SOFTWARE, HARDWARE, ACCESS, OTHER
            - suggestedPriority deve ser exatamente um dos valores: LOW, MEDIUM, HIGH, CRITICAL
            - confidence deve ser um número decimal entre 0.0 e 1.0
            - requiresHuman deve ser true se o problema for crítico
            - Use o contexto da base de conhecimento fornecido para embasar a solução
            """;
    
    public AnalysisResultDto analyze(TicketInputDto input) {
        log.info("Analisando ticket {} IA", input.ticketId());

        List<Document> relevantDocs = vectorStore.similaritySearch(
            SearchRequest.builder()
                .query(input.title() +" " +input.description())
                .topK(1)
                .similarityThreshold(0.4)
                .build()   
        );

        String context = relevantDocs.isEmpty()
            ? "Nenhum documento relevante foi encontrado na base de conhecimento"
            : relevantDocs.stream()
                .map(Document::getText)
                .reduce("", (a, b) -> a + "\n\n---\n\n" + b);

        log.info("RAG: {} documentos relevantes encontrados para o ticket {}", relevantDocs.size(), input.ticketId());

        String userPrompt = """
                Base de conhecimento relevante:
                %s

                Chamado a ser analisado:
                Título: %s
                Descrição: %s
                """.formatted(context, input.title(), input.description());

        String rawResponse = chatClient.prompt()
            .system(PROMPT)
            .user(userPrompt)
            .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, input.ticketId()))
            .call()
            .content();

        log.info("Resposta bruta da LLM para o ticket {}: {}", input.ticketId(), rawResponse);

        return parseResponse(rawResponse, relevantDocs);
    }

    private AnalysisResultDto parseResponse(String rawResponse, List<Document> relevantDocs) {
        try {
            String cleaned = rawResponse
                    .replaceAll("```json", "")
                    .replaceAll("```", "")
                    .trim();

            // Parse manual dos campos do JSON
            String category = extractJsonField(cleaned, "suggestedCategory");
            String priority = extractJsonField(cleaned, "suggestedPriority");
            String solution = extractJsonField(cleaned, "proposedSolution");
            double confidence = Double.parseDouble(extractJsonField(cleaned, "confidence"));
            boolean requiresHuman = Boolean.parseBoolean(extractJsonField(cleaned, "requiresHuman"));

            List<SourceDto> sources = relevantDocs.stream()
                    .map(doc -> {
                        String filename = doc.getMetadata().getOrDefault("source", "knowledge-base").toString();
                        String excerpt = doc.getText().substring(0, Math.min(150, doc.getText().length())) + "...";
                        return new SourceDto(filename, excerpt);
                    })
                    .toList();

            return new AnalysisResultDto(category, priority, solution, confidence, requiresHuman, sources);

        } catch (Exception ex) {
            log.error("Falha ao fazer parse da resposta do Gemini: {}. Resposta bruta: {}", ex.getMessage(), rawResponse);
            return fallbackResult();
        }
    }

    private String extractJsonField(String json, String fieldName) {
        String pattern = "\"" + fieldName + "\"";
        int keyIndex = json.indexOf(pattern);
        if (keyIndex == -1) throw new IllegalArgumentException("Campo não encontrado: " + fieldName);

        int colonIndex = json.indexOf(":", keyIndex);
        int valueStart = colonIndex + 1;

        while (valueStart < json.length() && Character.isWhitespace(json.charAt(valueStart))) {
            valueStart++;
        }

        if (json.charAt(valueStart) == '"') {
            int valueEnd = json.indexOf('"', valueStart + 1);
            return json.substring(valueStart + 1, valueEnd);
        }

        int valueEnd = valueStart;
        while (valueEnd < json.length()
                && json.charAt(valueEnd) != ','
                && json.charAt(valueEnd) != '}') {
            valueEnd++;
        }
        return json.substring(valueStart, valueEnd).trim();
    }

    private AnalysisResultDto fallbackResult() {
        return new AnalysisResultDto(
                "OTHER",
                "MEDIUM",
                "Não foi possível analisar automaticamente. Chamado escalado para atendimento humano.",
                0.0,
                true,
                List.of()
        );
    }
}
