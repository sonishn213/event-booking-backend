package com.projects.eventticket.eventticket.controllers;

import com.projects.eventticket.eventticket.domain.dtos.BecomeOrganizerRequestDto;
import com.projects.eventticket.eventticket.domain.dtos.CreateEventRequestDto;
import com.projects.eventticket.eventticket.domain.requests.BecomeOrganizerRequest;
import com.projects.eventticket.eventticket.mappers.UserMapper;
import com.projects.eventticket.eventticket.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.projects.eventticket.eventticket.utils.JwtUtil.parsUserId;

@RestController
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/become-organizer")
    private ResponseEntity<Void> becomeOrganizer(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody BecomeOrganizerRequestDto becomeOrganizerRequestDto
    ){
        BecomeOrganizerRequest becomeOrganizerRequest = userMapper.fromBecomeOrganizerRequestDto(becomeOrganizerRequestDto);
        userService.BecomeOrganizer(parsUserId(jwt),becomeOrganizerRequest);
        return ResponseEntity.noContent().build();
    }

}
