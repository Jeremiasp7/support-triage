# Problemas de Acesso e Permissões

## Usuário sem acesso ao sistema
**Sintomas:** login recusado, erro "acesso negado".
**Solução:**
1. Verificar se o usuário existe no Active Directory.
2. Confirmar se a conta está ativa e não bloqueada.
3. Redefinir senha se necessário.
4. Verificar se o usuário pertence ao grupo correto.
**Escalação:** Criação de novos usuários deve seguir o processo formal de RH.

## Acesso a pastas compartilhadas negado
**Sintomas:** "Você não tem permissão para acessar esta pasta".
**Solução:**
1. Verificar as permissões NTFS e de compartilhamento da pasta.
2. Confirmar se o usuário está no grupo AD correto.
3. Aguardar propagação das permissões (até 15 minutos).
**Escalação:** Novas permissões requerem aprovação do gestor do usuário.

## Conta bloqueada por excesso de tentativas
**Sintomas:** "Conta bloqueada. Tente novamente mais tarde".
**Solução:**
1. Desbloquear a conta no Active Directory.
2. Orientar o usuário a redefinir a senha.
3. Verificar scripts ou serviços usando senha antiga.
**Escalação:** Bloqueios repetidos podem indicar tentativa de acesso não autorizado.