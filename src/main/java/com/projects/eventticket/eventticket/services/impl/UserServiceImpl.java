package com.projects.eventticket.eventticket.services.impl;

import com.projects.eventticket.eventticket.domain.entity.OrganizerUser;
import com.projects.eventticket.eventticket.domain.entity.User;
import com.projects.eventticket.eventticket.domain.enums.UserRoleEnum;
import com.projects.eventticket.eventticket.domain.requests.BecomeOrganizerRequest;
import com.projects.eventticket.eventticket.exception.UserAlreadyOrganizerException;
import com.projects.eventticket.eventticket.exception.UserNotFoundException;
import com.projects.eventticket.eventticket.repository.OrganizerUserRepository;
import com.projects.eventticket.eventticket.repository.UserRepository;
import com.projects.eventticket.eventticket.services.IdentityProviderService;
import com.projects.eventticket.eventticket.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrganizerUserRepository organizerUserRepository;
    private final IdentityProviderService identityProviderService;

    @Override
    @Transactional
    public boolean BecomeOrganizer(UUID userId, BecomeOrganizerRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new UserNotFoundException(
                        String.format("User with ID '%s' not found",userId))
                );

        //check if already organizer entry exists
        boolean organizerExists = organizerUserRepository
                .findByUserId(userId)
                .isPresent();

        if(organizerExists){
            throw new UserAlreadyOrganizerException(
                    String.format("User with ID '%s' is already an Organizer",userId)
                );
        }

        OrganizerUser organizerUser = new OrganizerUser();

        organizerUser.setUser(user);
        organizerUser.setCompanyName(request.getCompanyName());
        organizerUser.setDisplayName(request.getDisplayName());
        organizerUser.setCompanyEmail(request.getCompanyEmail());
        organizerUser.setCompanyPhone(request.getCompanyPhone());
        organizerUser.setGstNumber(request.getGstNumber());
        organizerUser.setAddress(request.getAddress());

        organizerUserRepository.save(organizerUser);

        identityProviderService.assignRoleOrganizer(userId.toString());

        return true;
    }

}
