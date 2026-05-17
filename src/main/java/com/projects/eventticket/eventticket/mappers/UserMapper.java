package com.projects.eventticket.eventticket.mappers;

import com.projects.eventticket.eventticket.domain.dtos.BecomeOrganizerRequestDto;
import com.projects.eventticket.eventticket.domain.requests.BecomeOrganizerRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    BecomeOrganizerRequest fromBecomeOrganizerRequestDto(BecomeOrganizerRequestDto becomeOrganizerRequestDto);
}
