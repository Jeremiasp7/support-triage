package com.supporttriage.ticket_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// criado como record, mas isso pode mudar
public record TicketRequestDto(
    @NotBlank(message = "Titulo e obrigatorio")
    String title,

    @NotBlank(message = "Descricao e obrigatoria")
    String description,

    @Email(message = "Email invalido")
    String requesterEmail
) {}
