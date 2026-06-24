# Problemas de Rede

## Sem conexão com a internet
**Sintomas:** usuário não acessa sites externos, ping para 8.8.8.8 falha.
**Solução:**
1. Verificar cabo de rede ou conexão Wi-Fi.
2. Reiniciar o roteador e o switch local.
3. Verificar configurações de IP (deve ser automático via DHCP).
4. Acionar equipe de infraestrutura se o problema persistir.
**Escalação:** Após 30 minutos sem resolução, escalar para a equipe de redes.

## VPN não conecta
**Sintomas:** cliente VPN exibe erro de autenticação ou timeout.
**Solução:**
1. Confirmar credenciais do usuário no Active Directory.
2. Verificar se o servidor VPN está disponível (ping ao IP do gateway).
3. Reinstalar o cliente VPN.
4. Verificar se o firewall local está bloqueando as portas 443 ou 1194.
**Escalação:** Problemas de certificado devem ser escalados para Segurança da Informação.

## Lentidão na rede interna
**Sintomas:** transferência de arquivos lenta, latência alta entre máquinas da LAN.
**Solução:**
1. Identificar se o problema é pontual (uma máquina) ou geral (toda a rede).
2. Verificar utilização dos switches com ferramenta de monitoramento.
3. Procurar por loops de rede com o protocolo STP.
**Escalação:** Problemas generalizados devem ser escalados imediatamente para infra.