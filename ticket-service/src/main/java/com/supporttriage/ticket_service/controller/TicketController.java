package com.supporttriage.ticket_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supporttriage.ticket_service.dto.StatusUpdateRequestDto;
import com.supporttriage.ticket_service.dto.TicketRequestDto;
import com.supporttriage.ticket_service.dto.TicketResponseDto;
import com.supporttriage.ticket_service.service.TicketService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/tickets")
@RequiredArgsConstructor
public class TicketController {
    
    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponseDto> create(@Valid @RequestBody TicketRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDto> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(ticketService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<TicketResponseDto>> findAll() {
        return ResponseEntity.ok(ticketService.findAll());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TicketResponseDto> updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody StatusUpdateRequestDto request) {
        return ResponseEntity.ok(ticketService.updateStatus(id, request.status()));
    }
}
