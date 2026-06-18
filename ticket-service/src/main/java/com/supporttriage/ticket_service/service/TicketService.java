package com.supporttriage.ticket_service.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.supporttriage.ticket_service.domain.Ticket;
import com.supporttriage.ticket_service.domain.TicketStatus;
import com.supporttriage.ticket_service.dto.TicketRequestDto;
import com.supporttriage.ticket_service.dto.TicketResponseDto;
import com.supporttriage.ticket_service.exceptions.TicketNotFoundException;
import com.supporttriage.ticket_service.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {
    
    private static final Logger log = LoggerFactory.getLogger(TicketService.class);

    private final TicketRepository ticketRepository;

    public TicketResponseDto create(TicketRequestDto request) {
        Ticket ticket = new Ticket();
        ticket.setTitle(request.title());
        ticket.setDescription(request.description());
        ticket.setRequesterEmail(request.requesterEmail());

        Ticket saved = ticketRepository.save(ticket);
        log.info("Ticket criado: {}", saved.getId());
        
        // análise via ai-service quando o módulo for implementado

        return toResponse(saved);
    }

    public TicketResponseDto findById(UUID id) {
        Ticket ticket = ticketRepository.findById(id)
            .orElseThrow(() -> new TicketNotFoundException(id));
        
        return toResponse(ticket);
    }

    public List<TicketResponseDto> findAll() {
        return ticketRepository.findAll()
            .stream()
            .map(this::toResponse)
            .toList();
    }

    public TicketResponseDto updateStatus(UUID id, TicketStatus status) {
        Ticket ticket = ticketRepository.findById(id)
            .orElseThrow(() -> new TicketNotFoundException(id));
        ticket.setStatus(status);
        Ticket updated = ticketRepository.save(ticket);
        log.info("Ticket {} atualizado para status {}", id, status);
        return toResponse(updated);
    }

    private TicketResponseDto toResponse(Ticket ticket) {
        TicketResponseDto response = new TicketResponseDto();
        response.setId(ticket.getId());
        response.setTitle(ticket.getTitle());
        response.setDescription(ticket.getDescription());
        response.setStatus(ticket.getStatus());
        response.setPriority(ticket.getPriority());
        response.setCategory(ticket.getCategory());
        response.setRequesterEmail(ticket.getRequesterEmail());
        response.setCreatedAt(ticket.getCreatedAt());
        response.setUpdatedAt(ticket.getUpdatedAt());
        return response;
    }
}
