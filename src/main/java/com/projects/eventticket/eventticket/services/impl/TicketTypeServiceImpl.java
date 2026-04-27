package com.projects.eventticket.eventticket.services.impl;

import com.projects.eventticket.eventticket.config_properties.RazorPayConfigProperties;
import com.projects.eventticket.eventticket.domain.entity.Ticket;
import com.projects.eventticket.eventticket.domain.entity.TicketType;
import com.projects.eventticket.eventticket.domain.entity.User;
import com.projects.eventticket.eventticket.domain.enums.TicketStatusEnum;
import com.projects.eventticket.eventticket.exception.TicketSoldOutException;
import com.projects.eventticket.eventticket.exception.TicketTypeNotFoundException;
import com.projects.eventticket.eventticket.exception.UserNotFoundException;
import com.projects.eventticket.eventticket.payment_gateway.dtos.OrderDto;
import com.projects.eventticket.eventticket.payment_gateway.service.PaymentGatewayService;
import com.projects.eventticket.eventticket.repository.TicketRepository;
import com.projects.eventticket.eventticket.repository.TicketTypeRepository;
import com.projects.eventticket.eventticket.repository.UserRepository;
import com.projects.eventticket.eventticket.services.QrCodeService;
import com.projects.eventticket.eventticket.services.TicketTypeService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final UserRepository userRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final TicketRepository ticketRepository;
    private final QrCodeService qrCodeService;
    private final PaymentGatewayService paymentGatewayService;

    @Override
    @Transactional
    public OrderDto purchaseTicket(UUID userId, UUID ticketTypeId) throws RazorpayException {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(
                String.format("User with id '%s' not found",userId)
        ));

        var ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId).orElseThrow(()->
                new TicketTypeNotFoundException(String.format("Ticket type with id '%s'",ticketTypeId)
        ));

        // check if tickets available
        if(!isTicketAvailable(ticketType))
            throw new TicketSoldOutException();

        //Ticket savedTicket = createTicket(user,ticketType);

        //create razorpay order
        return paymentGatewayService.createOrder((int) (ticketType.getPrice() *  100), "INR");
//        return savedTicket;
    }

    private Ticket createTicket(User user, TicketType ticketType){
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
