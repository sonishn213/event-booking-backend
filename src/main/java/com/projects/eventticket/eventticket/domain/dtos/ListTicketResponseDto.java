package com.projects.eventticket.eventticket.domain.dtos;

import com.projects.eventticket.eventticket.domain.entity.TicketType;
import com.projects.eventticket.eventticket.domain.enums.TicketStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListTicketResponseDto {

    private UUID id;
    private TicketStatusEnum status;
    private ListTicketTicketTypeResponseDto ticketType;
}
