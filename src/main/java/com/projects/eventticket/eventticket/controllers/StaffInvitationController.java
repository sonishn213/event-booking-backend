package com.projects.eventticket.eventticket.controllers;

import com.projects.eventticket.eventticket.services.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.projects.eventticket.eventticket.utils.JwtUtil.parsUserId;

@RestController
@RequestMapping(path = "/api/v1/staffs-invitation")
@RequiredArgsConstructor
public class StaffInvitationController {
    private final StaffService staffService;

    @PostMapping("/accept/{invitationId}")
    public ResponseEntity<Void> acceptInvite(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID invitationId
    ){
        staffService.acceptInvite(parsUserId(jwt), invitationId);
        return ResponseEntity.noContent().build();

    }
}
