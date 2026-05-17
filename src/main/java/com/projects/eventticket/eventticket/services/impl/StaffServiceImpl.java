package com.projects.eventticket.eventticket.services.impl;

import com.projects.eventticket.eventticket.domain.dtos.ListStaffResponseDto;
import com.projects.eventticket.eventticket.domain.entity.OrganizerUser;
import com.projects.eventticket.eventticket.domain.entity.StaffInvites;
import com.projects.eventticket.eventticket.domain.entity.User;
import com.projects.eventticket.eventticket.email.InviteStaffMail;
import com.projects.eventticket.eventticket.exception.InvitationNotFoundException;
import com.projects.eventticket.eventticket.exception.StaffAlreadyBelongsException;
import com.projects.eventticket.eventticket.exception.UserNotFoundException;
import com.projects.eventticket.eventticket.mappers.StaffMapper;
import com.projects.eventticket.eventticket.repository.OrganizerUserRepository;
import com.projects.eventticket.eventticket.repository.StaffInviteRepository;
import com.projects.eventticket.eventticket.repository.UserRepository;
import com.projects.eventticket.eventticket.services.IdentityProviderService;
import com.projects.eventticket.eventticket.services.StaffService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {
    private final UserRepository userRepository;
    private final OrganizerUserRepository organizerUserRepository;
    private final IdentityProviderService identityProviderService;
    private final StaffMapper staffMapper;
    private final InviteStaffMail inviteStaffMail;
    private final StaffInviteRepository staffInviteRepository;

    @Override
    public Page<ListStaffResponseDto> listStaff(UUID organizerId, Pageable pageable) {
        return null;
    }

    @Override
    public void invite(UUID organizerId, String email) throws MessagingException, IOException {

        //check if staff exists
        boolean staffExists  = organizerUserRepository
                .existsByUserIdAndStaffsEmail(organizerId,email);

        if(staffExists){
            throw new StaffAlreadyBelongsException("Staff already belongs to you");
        }

        OrganizerUser organizerUser = organizerUserRepository
                .findByUserId(organizerId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User with ID '%s' not found", organizerId))
                );

        //send invite
        StaffInvites staffInvites = new StaffInvites();
        staffInvites.setStaffEmail(email);
        staffInvites.setOrganizerUser(organizerUser);
        var savedInvite = staffInviteRepository.saveAndFlush(staffInvites);

        inviteStaffMail.send(email,savedInvite.getId());
    }

    @Override
    public void acceptInvite(UUID StaffId, UUID invitationId) {
        StaffInvites staffInvites = staffInviteRepository.findById(invitationId)
                .orElseThrow(()->new InvitationNotFoundException(
                        "Invitation not found exception"
                ));

        userRepository.findByEmail(staffInvites.getStaffEmail());

    }

}
