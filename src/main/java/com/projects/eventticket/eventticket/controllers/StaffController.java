package com.projects.eventticket.eventticket.controllers;

import com.projects.eventticket.eventticket.email.InviteStaffMail;
import com.projects.eventticket.eventticket.services.StaffService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

import static com.projects.eventticket.eventticket.utils.JwtUtil.parsUserId;

@RestController
@RequestMapping(path = "/api/v1/staffs")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;
    private final InviteStaffMail inviteStaffMail;

    @GetMapping
    public ResponseEntity<Void> listStaff(
            @AuthenticationPrincipal Jwt jwt, Pageable pageable
    ){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/invite/{email}")
    public ResponseEntity<Void> invite(
            @AuthenticationPrincipal Jwt jwt,@PathVariable String email
    ) throws MessagingException, IOException {
        staffService.invite(parsUserId(jwt), email);
        return ResponseEntity.noContent().build();
    }
}
