# Critérios de Escalação para Atendimento Humano

## Quando escalar imediatamente
- Incidentes que afetam múltiplos usuários simultaneamente.
- Problemas relacionados à segurança da informação.
- Interrupção de sistemas críticos por mais de 15 minutos.
- Falhas de hardware com risco de perda de dados.
- Usuário reporta urgência extrema com impacto no negócio.

## Quando a IA deve encerrar e escalar
- Confiança na solução proposta abaixo de 60%.
- Problema não encontrado na base de conhecimento após 3 tentativas.
- Usuário expressamente solicita atendimento humano.
- Mais de 2 soluções tentadas sem sucesso relatado pelo usuário.

## Níveis de prioridade
- **CRITICAL:** sistema de produção fora do ar → SLA 15 min.
- **HIGH:** múltiplos usuários afetados, sem workaround → SLA 1 hora.
- **MEDIUM:** usuário único, com workaround disponível → SLA 4 horas.
- **LOW:** dúvidas, melhorias, solicitações não urgentes → SLA 24 horas.

## Categorias de chamados
- **NETWORK:** problemas de conectividade, VPN, rede interna.
- **SOFTWARE:** erros de aplicação, falhas de software, bugs.
- **HARDWARE:** falhas físicas de equipamentos.
- **ACCESS:** problemas de login, permissões, contas bloqueadas.
- **OTHER:** qualquer situação que não se enquadre nas categorias acima.