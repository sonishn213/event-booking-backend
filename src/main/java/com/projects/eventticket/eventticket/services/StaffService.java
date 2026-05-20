package com.projects.eventticket.eventticket.services;

import com.projects.eventticket.eventticket.domain.dtos.ListStaffResponseDto;
import com.projects.eventticket.eventticket.domain.dtos.ListStaffsOrganizersDto;
import com.projects.eventticket.eventticket.domain.dtos.PageWrapperDto;
import com.projects.eventticket.eventticket.domain.entity.Event;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.UUID;

public interface StaffService {
    Page<ListStaffResponseDto> listStaff(UUID organizerId, Pageable pageable);

    void invite(UUID organizerId, String email) throws MessagingException, IOException;

    void acceptInvite(UUID StaffId, UUID invitationId);

    PageWrapperDto<ListStaffsOrganizersDto> organizersByStaffId(UUID staffId,Pageable pageable);
}
