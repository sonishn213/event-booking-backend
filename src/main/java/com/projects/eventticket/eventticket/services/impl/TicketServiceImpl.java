package com.projects.eventticket.eventticket.services.impl;

import com.projects.eventticket.eventticket.domain.entity.Ticket;
import com.projects.eventticket.eventticket.repository.TicketRepository;
import com.projects.eventticket.eventticket.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;

    @Override
    public Page<Ticket> listTicketsForUser(UUID userId, Pageable pageable) {
        Page<Ticket> tickets = ticketRepository.findByPurchaserId(userId,pageable);
        return tickets;
    }

    @Override
    public Optional<Ticket> getTicketForUser(UUID userId, UUID ticketId) {
        return ticketRepository.findByIdAndPurchaserId(ticketId,userId);
    }
}
