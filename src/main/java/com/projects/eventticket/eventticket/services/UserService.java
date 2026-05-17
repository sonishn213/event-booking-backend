package com.projects.eventticket.eventticket.services;

import com.projects.eventticket.eventticket.domain.dtos.BecomeOrganizerRequestDto;
import com.projects.eventticket.eventticket.domain.requests.BecomeOrganizerRequest;

import java.util.UUID;

public interface UserService {
    boolean BecomeOrganizer(UUID userId, BecomeOrganizerRequest becomeOrganizerRequestDto);
}
