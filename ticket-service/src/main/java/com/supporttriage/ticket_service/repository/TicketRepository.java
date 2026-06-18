package com.supporttriage.ticket_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supporttriage.ticket_service.domain.Ticket;
import com.supporttriage.ticket_service.domain.TicketStatus;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    List<Ticket> findByStatus(TicketStatus status);
    List<Ticket> findByRequesterEmail(String email);
}
