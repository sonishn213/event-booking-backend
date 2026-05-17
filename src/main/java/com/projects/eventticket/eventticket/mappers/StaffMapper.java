package com.projects.eventticket.eventticket.mappers;

import com.projects.eventticket.eventticket.domain.dtos.ListStaffResponseDto;
import com.projects.eventticket.eventticket.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StaffMapper {
    ListStaffResponseDto toListStaffResponseDto(User user);
}
