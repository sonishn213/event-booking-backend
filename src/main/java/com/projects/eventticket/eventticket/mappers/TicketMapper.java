package com.projects.eventticket.eventticket.mappers;

import com.projects.eventticket.eventticket.domain.dtos.GetTicketResponseDto;
import com.projects.eventticket.eventticket.domain.dtos.ListTicketResponseDto;
import com.projects.eventticket.eventticket.domain.dtos.ListTicketTicketTypeResponseDto;
import com.projects.eventticket.eventticket.domain.entity.Ticket;
import com.projects.eventticket.eventticket.domain.entity.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {
    ListTicketTicketTypeResponseDto toListTicketTicketTypeResponseDto(TicketType ticketType);

    ListTicketResponseDto toListTicketResponseDto(Ticket ticket);

    @Mapping(target = "price", source = "ticket.ticketType.price")
    @Mapping(target = "description", source = "ticket.ticketType.description")
    @Mapping(target = "eventName", source = "ticket.ticketType.event.name")
    @Mapping(target = "eventStart", source = "ticket.ticketType.event.start")
    @Mapping(target = "eventEnd", source = "ticket.ticketType.event.end")
    @Mapping(target = "eventVenue", source = "ticket.ticketType.event.venue")
    GetTicketResponseDto toGetTicketResponseDto(Ticket ticket);

}
