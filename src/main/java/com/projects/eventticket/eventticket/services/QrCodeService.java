package com.projects.eventticket.eventticket.services;

import com.projects.eventticket.eventticket.domain.entity.QrCode;
import com.projects.eventticket.eventticket.domain.entity.Ticket;

import java.util.UUID;

public interface QrCodeService {

   QrCode generateQrCode(Ticket ticket);

   byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId);
}
