package com.projects.eventticket.eventticket.services.impl;

import com.projects.eventticket.eventticket.domain.entity.Ticket;
import com.projects.eventticket.eventticket.domain.entity.TicketType;
import com.projects.eventticket.eventticket.domain.entity.User;
import com.projects.eventticket.eventticket.domain.enums.TicketStatusEnum;
import com.projects.eventticket.eventticket.exception.TicketSoldOutException;
import com.projects.eventticket.eventticket.exception.TicketTypeNotFoundException;
import com.projects.eventticket.eventticket.exception.UserNotFoundException;
import com.projects.eventticket.eventticket.repository.TicketRepository;
import com.projects.eventticket.eventticket.repository.TicketTypeRepository;
import com.projects.eventticket.eventticket.repository.UserRepository;
import com.projects.eventticket.eventticket.services.QrCodeService;
import com.projects.eventticket.eventticket.services.TicketTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final UserRepository userRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final TicketRepository ticketRepository;
    private final QrCodeService qrCodeService;

    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(
                String.format("User with id '%s' not found",userId)
        ));

        var ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId).orElseThrow(()->
                new TicketTypeNotFoundException(String.format("Ticket type with id '%s'",ticketTypeId)
        ));

        // check if tickets available
        if(isTicketAvailable(ticketType))
            throw new TicketSoldOutException();

        Ticket ticket = new Ticket();
        ticket.setStatus(TicketStatusEnum.PURCHASED);
        ticket.setTicketType(ticketType);
        ticket.setPurchaser(user);

        Ticket savedTicket = ticketRepository.saveAndFlush(ticket);
        qrCodeService.generateQrCode(savedTicket);

        ticketRepository.save(savedTicket);
        return savedTicket;
    }

    private boolean isTicketAvailable(TicketType ticketType) {
        int purchasedTickets = ticketRepository.countByTicketTypeId(ticketType.getId());

        Integer totalAvailable = ticketType.getTotalAvailable();

        return purchasedTickets + 1 <= totalAvailable;
    }
}
