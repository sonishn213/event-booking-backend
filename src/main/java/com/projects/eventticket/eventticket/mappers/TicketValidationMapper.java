package com.projects.eventticket.eventticket.mappers;

import com.projects.eventticket.eventticket.domain.dtos.TicketValidationResponseDto;
import com.projects.eventticket.eventticket.domain.entity.TicketValidation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketValidationMapper {

    @Mapping(target = "ticketId", source = "ticket.id")
    TicketValidationResponseDto toTickerValidationResponseDto(TicketValidation ticketValidation);
}
