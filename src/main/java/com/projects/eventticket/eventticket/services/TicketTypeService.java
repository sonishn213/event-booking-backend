package com.projects.eventticket.eventticket.services;

import com.projects.eventticket.eventticket.payment_gateway.dtos.CallBackDto;
import com.projects.eventticket.eventticket.payment_gateway.dtos.OrderDto;
import com.razorpay.RazorpayException;
import jakarta.transaction.Transactional;

import java.util.UUID;

public interface TicketTypeService {
    OrderDto purchaseTicket(UUID userId, UUID ticketTypeId) throws RazorpayException;

    @Transactional
    boolean verifyPurchase(UUID userId, UUID ticketTypeId, CallBackDto RazorPayCallbackResponse) throws RazorpayException;
}
