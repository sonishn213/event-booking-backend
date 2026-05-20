package com.projects.eventticket.eventticket.mappers;

import com.projects.eventticket.eventticket.domain.dtos.ListStaffsOrganizersDto;
import com.projects.eventticket.eventticket.domain.entity.OrganizerUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrganizerMapper {
    ListStaffsOrganizersDto toListStaffsOrganizersDto(OrganizerUser user);
}
