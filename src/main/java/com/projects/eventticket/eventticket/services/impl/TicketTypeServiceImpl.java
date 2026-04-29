package com.projects.eventticket.eventticket.services.impl;

import com.projects.eventticket.eventticket.domain.entity.Ticket;
import com.projects.eventticket.eventticket.domain.entity.TicketPurchase;
import com.projects.eventticket.eventticket.domain.entity.TicketType;
import com.projects.eventticket.eventticket.domain.entity.User;
import com.projects.eventticket.eventticket.domain.enums.TicketPurchaseMethodEnum;
import com.projects.eventticket.eventticket.domain.enums.TicketPurchaseStatusEnum;
import com.projects.eventticket.eventticket.domain.enums.TicketStatusEnum;
import com.projects.eventticket.eventticket.exception.TicketSoldOutException;
import com.projects.eventticket.eventticket.exception.TicketTypeNotFoundException;
import com.projects.eventticket.eventticket.exception.UserNotFoundException;
import com.projects.eventticket.eventticket.payment_gateway.dtos.CallBackDto;
import com.projects.eventticket.eventticket.payment_gateway.dtos.OrderDto;
import com.projects.eventticket.eventticket.payment_gateway.dtos.PaymentDto;
import com.projects.eventticket.eventticket.payment_gateway.service.PaymentGatewayService;
import com.projects.eventticket.eventticket.repository.TicketPurchaseRepository;
import com.projects.eventticket.eventticket.repository.TicketRepository;
import com.projects.eventticket.eventticket.repository.TicketTypeRepository;
import com.projects.eventticket.eventticket.repository.UserRepository;
import com.projects.eventticket.eventticket.services.QrCodeService;
import com.projects.eventticket.eventticket.services.TicketTypeService;
import com.razorpay.Payment;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
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
    private final PaymentGatewayService paymentGatewayService;
    private final TicketPurchaseRepository ticketPurchaseRepository;

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

        int amount = (int) (ticketType.getPrice() *  100);
        String currency = "INR";
        //create razorpay order
        OrderDto order = paymentGatewayService.createOrder(amount, currency);
        createTicketPurchase(user,ticketType,order.getId(),amount,currency);

        return order;
    }

    @Override
    @Transactional
    public boolean verifyPurchase(UUID userId, UUID ticketTypeId, CallBackDto RazorPayCallbackResponse) throws RazorpayException {
        //verify with gateway
        boolean isValid = paymentGatewayService.verifyPurchase(RazorPayCallbackResponse);
        if(!isValid){
            return false;
        }

        var ticketType = ticketTypeRepository.findById(ticketTypeId).orElseThrow(()->
            new TicketTypeNotFoundException(String.format("Ticket type with id '%s'",ticketTypeId)
        ));

        var user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(
                String.format("User with id '%s' not found",userId)
        ));

        PaymentDto paymentDto = paymentGatewayService.getPaymentById(RazorPayCallbackResponse.getPaymentId());

        //generate ticket and
        //make changes to ticket purchase
        updateTicketPurchaseSuccess(user,ticketType,paymentDto,RazorPayCallbackResponse);

        return true;
    }

    private void updateTicketPurchaseSuccess(User user,TicketType ticketType,PaymentDto paymentDto,CallBackDto RazorPayCallbackResponse){
        Ticket ticket = createTicket(user,ticketType);

        var ticketPurchase = ticketPurchaseRepository
                .findByRazorpayOrderId(RazorPayCallbackResponse.getOrderId());

        ticketPurchase.setStatus(paymentDto.getStatus());
        ticketPurchase.setCustomerContact(paymentDto.getContact());
        ticketPurchase.setMethod(paymentDto.getMethod());
        ticketPurchase.setTicket(ticket);
        ticketPurchase.setRazorpayPaymentId(RazorPayCallbackResponse.getPaymentId());
        ticketPurchase.setRazorpaySignature(RazorPayCallbackResponse.getSignature());
        ticketPurchase.setRazorpayPaymentResponse(paymentDto.getResponseString());

        ticketPurchaseRepository.save(ticketPurchase);
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

    private void createTicketPurchase(
            User user,
            TicketType ticketType,
            String orderId,
            int amount,
            String currency)
    {
        TicketPurchase ticketPurchase = new TicketPurchase();
        ticketPurchase.setPurchaser(user);
        ticketPurchase.setCurrency(currency);
        ticketPurchase.setAmount(amount);
        ticketPurchase.setStatus(TicketPurchaseStatusEnum.INITIATED);
        ticketPurchase.setCustomerName(user.getName());
        ticketPurchase.setCustomerEmail(user.getEmail());
        ticketPurchase.setTicketType(ticketType);
        ticketPurchase.setRazorpayOrderId(orderId);

        ticketPurchaseRepository.saveAndFlush(ticketPurchase);

    }

    private boolean isTicketAvailable(TicketType ticketType) {
        int purchasedTickets = ticketRepository.countByTicketTypeId(ticketType.getId());

        Integer totalAvailable = ticketType.getTotalAvailable();

        return purchasedTickets + 1 <= totalAvailable;
    }
}
