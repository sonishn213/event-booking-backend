package com.projects.eventticket.eventticket.services;

import com.projects.eventticket.eventticket.domain.enums.UserRoleEnum;

import java.util.UUID;

public interface IdentityProviderService {
    void assignRoleOrganizer(String userId,UserRoleEnum userRole);
}
