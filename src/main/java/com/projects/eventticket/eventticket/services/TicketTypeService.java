package com.projects.eventticket.eventticket.services;

import com.projects.eventticket.eventticket.domain.entity.Ticket;
import com.projects.eventticket.eventticket.payment_gateway.dtos.OrderDto;
import com.razorpay.Order;
import com.razorpay.RazorpayException;

import java.util.UUID;

public interface TicketTypeService {
    OrderDto purchaseTicket(UUID userId, UUID ticketTypeId) throws RazorpayException;
}
