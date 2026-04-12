package com.projects.eventticket.eventticket.mappers;


import com.projects.eventticket.eventticket.domain.dtos.*;
import com.projects.eventticket.eventticket.domain.entity.Event;
import com.projects.eventticket.eventticket.domain.entity.Ticket;
import com.projects.eventticket.eventticket.domain.entity.TicketType;
import com.projects.eventticket.eventticket.domain.requests.CreateEventRequest;
import com.projects.eventticket.eventticket.domain.requests.CreateTicketTypeRequest;
import com.projects.eventticket.eventticket.domain.requests.UpdateEventRequest;
import com.projects.eventticket.eventticket.domain.requests.UpdateTicketTypeRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventsMapper {

    CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto);

    CreateEventRequest fromDto(CreateEventRequestDto dto);

    CreateEventResponseDto toDto(Event event);

    ListEventTicketTypeResponseDto toListEventTicketTypeResponseDto(TicketType ticketType);

    ListEventResponseDto toListEventResponseDto(Event event);

    GetEventDetailsTicketTypeResponseDto toGetEventDetailsTicketTypeResponseDto(TicketType ticketType);

    GetEventDetailsResponseDto toGetEventDetailsResponseDto(Event event);

    UpdateTicketTypeRequest fromDto(UpdateTicketTypeRequestDto dto);

    UpdateEventRequest fromDto(UpdateEventRequestDto dto);

    UpdateTicketTypeResponseDto toUpdateTicketTypeResponseDto(TicketType ticktType);

    UpdateEventResponseDto toUpdateEventResponseDto(Event event);

    ListPublishedEventResponseDto toListPublishedEventResponseDto(Event event);

    GetPublishedEventDetailsResponseDto toGetPublishedEventDetailsResponseDto(Event events);

    GetPublishedEventDetailsTicketTypeResponseDto toGetPublishedEventDetailsTicketTypeResponseDto(TicketType ticketType);
}
