package com.projects.eventticket.eventticket.controllers;

import com.projects.eventticket.eventticket.domain.dtos.TicketValidationRequestDto;
import com.projects.eventticket.eventticket.domain.dtos.TicketValidationResponseDto;
import com.projects.eventticket.eventticket.domain.entity.TicketValidation;
import com.projects.eventticket.eventticket.domain.enums.TicketValidationMethod;
import com.projects.eventticket.eventticket.mappers.TicketValidationMapper;
import com.projects.eventticket.eventticket.services.TicketValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/ticket-validations")
public class TicketValidationController {

    final TicketValidationMapper ticketValidationMapper;
    final TicketValidationService ticketValidationService;

    @PostMapping
    public ResponseEntity<TicketValidationResponseDto> validateTicket(
            @RequestBody TicketValidationRequestDto ticketValidationRequestDto
    ) {
        TicketValidationMethod method = ticketValidationRequestDto.getMethod();

        TicketValidation ticketValidation;

        if (TicketValidationMethod.MANUAL.equals(method)) {
            ticketValidation = ticketValidationService.validateTicketManually(
                    ticketValidationRequestDto.getId()
            );
        } else {
            ticketValidation = ticketValidationService.validateTicketByQrCode(
                    ticketValidationRequestDto.getId()
            );
        }

        return ResponseEntity.ok(
                ticketValidationMapper.toTickerValidationResponseDto(ticketValidation)
        );
    }
}
