package com.projects.eventticket.eventticket.controllers;

import com.projects.eventticket.eventticket.payment_gateway.dtos.CallBackDto;
import com.projects.eventticket.eventticket.payment_gateway.dtos.OrderDto;
import com.projects.eventticket.eventticket.services.TicketTypeService;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.projects.eventticket.eventticket.utils.JwtUtil.parsUserId;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/api/v1/events/{eventId}/ticket-types")
public class TicketTypeController {

    private final TicketTypeService ticketTypeService;

    @PostMapping(path="/{ticketTypeId}/tickets/purchase")
    private ResponseEntity<OrderDto> purchaseTicket(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID ticketTypeId
    ) throws RazorpayException {

        OrderDto orderResponse = ticketTypeService.purchaseTicket(parsUserId(jwt),ticketTypeId);
        return ResponseEntity.ok(orderResponse);
    }

    @PostMapping(path="/{ticketTypeId}/tickets/purchase-verify")
    private ResponseEntity<Void> verifyPurchase(
        @AuthenticationPrincipal Jwt jwt,
        @PathVariable UUID ticketTypeId,
        @RequestBody CallBackDto RazorPayDetails
    ) throws RazorpayException {

        //check valid
        boolean isValid = ticketTypeService
                .verifyPurchase(parsUserId(jwt), ticketTypeId, RazorPayDetails);

        if(isValid){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
