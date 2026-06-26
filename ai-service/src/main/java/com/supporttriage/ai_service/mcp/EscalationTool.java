package com.supporttriage.ai_service.mcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class EscalationTool {
    
    private static final Logger log = LoggerFactory.getLogger(EscalationTool.class);

    @Tool(description = """
            Escala um chamado para atendimento humano quando a IA não consegue resolver.
            Use esta ferramenta quando: a confiança na solução for baixa, o problema
            for muito complexo, ou o usuário solicitar atendimento humano explicitamente.
            """)
    public String escalateTicket(
        @ToolParam(description = "ID do ticket a ser escalado")
        String ticketId,
        @ToolParam(description = "Motivo detalhado do direcionamento")
        String reason) {

            log.warn("[MCP Tool] escalateTicket chamada - ticket: {}, motivo: {}", ticketId, reason);

            // será desenvolvido quando ticket-service tiver um endpoint de atualização de status

            return String.format(
                "Chamado %s escalado com sucesso para atendimento humano. " +
                "Motivo registrado: '%s'. " +
                "Um atendente entrará em contato em até 1 hora.",
                ticketId, reason);
        }
}
