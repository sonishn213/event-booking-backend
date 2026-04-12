package com.projects.eventticket.eventticket.services;

import com.projects.eventticket.eventticket.domain.entity.Ticket;

import java.util.UUID;

public interface TicketTypeService {
    Ticket purchaseTicket(UUID userId, UUID ticketTypeId);
}
