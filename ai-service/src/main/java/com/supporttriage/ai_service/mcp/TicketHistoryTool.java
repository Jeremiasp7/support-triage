package com.supporttriage.ai_service.mcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class TicketHistoryTool {
    
    private static final Logger log = LoggerFactory.getLogger(TicketHistoryTool.class);

    @Tool(description = """
            Busca chamados de suporte anteriores similares com base na categoria informada.
            Use esta ferramenta quando precisar de contexto histórico para resolver um problema.
            Retorna uma lista de soluções que funcionaram em casos anteriores.
        """)
    public String getTicketHistory(
        @ToolParam(description = "Categoria do chamado: NETWORK, SOFTWARE, HARDWARE ou OTHER") String category) {

            log.info("[MCP Tool] getTicketHistory chamada - categoria: {}", category);

            return switch(category.toUpperCase()) {
                case "NETWORK" -> """
                    1. VPN com erro de autenticação → solução: renovar certificado do cliente VPN. Resolvido em 95% dos casos.
                    2. Sem internet → solução: reiniciar roteador + verificar cabo. Resolvido em 80% dos casos.
                    3. Lentidão na rede → solução: verificar loop STP no switch. Resolvido em 70% dos casos.
                    """;
                case "SOFTWARE" -> """
                    Chamados anteriores de SOFTWARE resolvidos:
                    1. OutOfMemoryError → solução: aumentar heap JVM para -Xmx1024m. Resolvido em 85% dos casos.
                    2. Porta em uso → solução: identificar e encerrar processo via netstat. Resolvido em 99% dos casos.
                    3. API externa 503 → solução: verificar status do serviço e renovar credenciais. Resolvido em 60% dos casos.
                    """;
                case "HARDWARE" -> """
                    Chamados anteriores de HARDWARE resolvidos:
                    1. BSOD frequente → solução: substituição de RAM. Resolvido em 75% dos casos.
                    2. HD com falha → solução: backup imediato + substituição. Resolvido em 100% dos casos.
                    3. Computador não liga → solução: troca de fonte de alimentação. Resolvido em 65% dos casos.
                    """;
                default -> "Nenhum histórico disponível para a categoria informada.";
            };
        }

}
