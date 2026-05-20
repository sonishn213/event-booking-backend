package com.projects.eventticket.eventticket.services.impl;

import com.projects.eventticket.eventticket.domain.dtos.ListStaffResponseDto;
import com.projects.eventticket.eventticket.domain.dtos.ListStaffsOrganizersDto;
import com.projects.eventticket.eventticket.domain.dtos.PageWrapperDto;
import com.projects.eventticket.eventticket.domain.entity.OrganizerUser;
import com.projects.eventticket.eventticket.domain.entity.StaffInvites;
import com.projects.eventticket.eventticket.domain.entity.User;
import com.projects.eventticket.eventticket.domain.enums.UserRoleEnum;
import com.projects.eventticket.eventticket.email.InviteStaffMail;
import com.projects.eventticket.eventticket.exception.InvitationNotFoundException;
import com.projects.eventticket.eventticket.exception.StaffAlreadyBelongsException;
import com.projects.eventticket.eventticket.exception.UserNotFoundException;
import com.projects.eventticket.eventticket.mappers.OrganizerMapper;
import com.projects.eventticket.eventticket.mappers.StaffMapper;
import com.projects.eventticket.eventticket.repository.OrganizerUserRepository;
import com.projects.eventticket.eventticket.repository.StaffInviteRepository;
import com.projects.eventticket.eventticket.repository.UserRepository;
import com.projects.eventticket.eventticket.services.IdentityProviderService;
import com.projects.eventticket.eventticket.services.StaffService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {
    private final UserRepository userRepository;
    private final OrganizerUserRepository organizerUserRepository;
    private final IdentityProviderService identityProviderService;
    private final StaffMapper staffMapper;
    private final InviteStaffMail inviteStaffMail;
    private final StaffInviteRepository staffInviteRepository;
    private final OrganizerMapper organizerMapper;

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
    @Transactional
    public void acceptInvite(UUID StaffId, UUID invitationId) {
        StaffInvites staffInvites = staffInviteRepository.findById(invitationId)
                .orElseThrow(()->new InvitationNotFoundException(
                        "Invitation not found exception"
                ));

        //get staff
        User staff = userRepository.findByIdAndEmail(StaffId,staffInvites.getStaffEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        //get organizerUser
        OrganizerUser organizerUser = staffInvites.getOrganizerUser();

        organizerUser.getStaffs().add(staff);
        // save organizerUser
        organizerUserRepository.save(organizerUser);
        staffInviteRepository.delete(staffInvites);

        // send request to keycloak to put the staff role in staff
        identityProviderService.assignRoleOrganizer(StaffId.toString(), UserRoleEnum.ROLE_STAFF);
    }

    public PageWrapperDto<ListStaffsOrganizersDto> organizersByStaffId(
            UUID staffId,
            Pageable pageable)
    {
        Page<OrganizerUser> organizerUsers = organizerUserRepository
                .findByStaffs_Id(staffId,pageable);

        return new PageWrapperDto<>(
                organizerUsers.map(organizerMapper::toListStaffsOrganizersDto).toList(),
                organizerUsers.getNumber(),
                organizerUsers.getSize(),
                organizerUsers.getTotalPages()
        );
    }

}
