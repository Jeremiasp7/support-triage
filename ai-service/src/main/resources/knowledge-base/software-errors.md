# Erros de Software

## Aplicação não inicia (erro de porta em uso)
**Sintomas:** log exibe "Address already in use".
**Solução:**
1. Identificar o processo ocupando a porta: `netstat -ano | findstr :PORTA` (Windows).
2. Finalizar o processo conflitante ou configurar uma porta alternativa.
**Escalação:** Se o processo conflitante for crítico, escalar para o time de desenvolvimento.

## Erro de memória (OutOfMemoryError)
**Sintomas:** aplicação trava com log "java.lang.OutOfMemoryError".
**Solução:**
1. Aumentar heap da JVM: `-Xmx512m` para `-Xmx1024m`.
2. Analisar heap dump para identificar vazamentos de memória.
**Escalação:** Vazamentos confirmados devem ser tratados pela equipe de desenvolvimento.

## Falha de dependência (serviço externo indisponível)
**Sintomas:** erros 503 ou timeout ao consumir APIs externas.
**Solução:**
1. Verificar status do serviço externo.
2. Confirmar se as credenciais de API não expiraram.
3. Verificar regras de firewall para saída de tráfego.
**Escalação:** Indisponibilidade por mais de 15 minutos requer escalação imediata.