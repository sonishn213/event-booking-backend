package com.projects.eventticket.eventticket.controllers;

import com.projects.eventticket.eventticket.domain.dtos.ListStaffsOrganizersDto;
import com.projects.eventticket.eventticket.domain.dtos.PageWrapperDto;
import com.projects.eventticket.eventticket.services.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.projects.eventticket.eventticket.utils.JwtUtil.parsUserId;

@RestController
@RequestMapping(path = "/api/v1/staff")
@RequiredArgsConstructor
public class StaffOrganizerController {

    private final StaffService staffService;

    @GetMapping("/organizers")
    public ResponseEntity<Page<ListStaffsOrganizersDto>> staffOrganizers(
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable)
    {
        PageWrapperDto<ListStaffsOrganizersDto> organizers = staffService.organizersByStaffId(parsUserId(jwt),pageable);

        return ResponseEntity.ok(
            organizers.toPage(pageable)
        );
    }
}
