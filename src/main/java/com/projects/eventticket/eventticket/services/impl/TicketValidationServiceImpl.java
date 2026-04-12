package com.projects.eventticket.eventticket.services.impl;

import com.projects.eventticket.eventticket.domain.entity.QrCode;
import com.projects.eventticket.eventticket.domain.entity.Ticket;
import com.projects.eventticket.eventticket.domain.entity.TicketValidation;
import com.projects.eventticket.eventticket.domain.enums.QrCodeStatusEnum;
import com.projects.eventticket.eventticket.domain.enums.TicketValidationMethod;
import com.projects.eventticket.eventticket.domain.enums.TicketValidationStatusEnum;
import com.projects.eventticket.eventticket.exception.QrCodeNotFoundException;
import com.projects.eventticket.eventticket.exception.TicketNotFoundException;
import com.projects.eventticket.eventticket.repository.QrCodeRepository;
import com.projects.eventticket.eventticket.repository.TicketRepository;
import com.projects.eventticket.eventticket.repository.TicketValidationRepository;
import com.projects.eventticket.eventticket.services.QrCodeService;
import com.projects.eventticket.eventticket.services.TicketValidationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketValidationServiceImpl implements TicketValidationService {

    final QrCodeRepository qrCodeRepository;
    final TicketValidationRepository ticketValidationRepository;
    final TicketRepository ticketRepository;

    @Override
    public TicketValidation validateTicketByQrCode(UUID qrCodeId) {
        QrCode qrCode = qrCodeRepository.findByIdAndStatus(qrCodeId, QrCodeStatusEnum.ACTIVE)
                .orElseThrow(() -> new QrCodeNotFoundException(
                    String.format(
                        "Qr code with ID '%s' not found", qrCodeId
                    )
                ));

        Ticket ticket = qrCode.getTicket();
        return validateTicket(ticket,TicketValidationMethod.QR_SCAN);
    }

    @Override
    public TicketValidation validateTicketManually(UUID ticketId) {
        Ticket ticket = ticketRepository
                .findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException(
                    String.format("Ticket with id '%s' not found", ticketId)
                ));

        return validateTicket(ticket,TicketValidationMethod.MANUAL);
    }

    private TicketValidation validateTicket(Ticket ticket,TicketValidationMethod method){
        TicketValidation ticketValidation = new TicketValidation();

        ticketValidation.setTicket(ticket);
        ticketValidation.setValidationMethod(method);

        TicketValidationStatusEnum ticketValidationStatus =
                ticket.getValidations().stream()
                        .filter(v -> TicketValidationStatusEnum.VALID.equals(v.getStatus()))
                        .findFirst()
                        .map(v -> TicketValidationStatusEnum.INVALID)
                        .orElse(TicketValidationStatusEnum.VALID);

        ticketValidation.setStatus(ticketValidationStatus);

        return ticketValidationRepository.save(ticketValidation);
    }
}
