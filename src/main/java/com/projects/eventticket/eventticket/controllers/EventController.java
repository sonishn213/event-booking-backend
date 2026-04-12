package com.projects.eventticket.eventticket.controllers;

import com.projects.eventticket.eventticket.domain.dtos.*;
import com.projects.eventticket.eventticket.domain.entity.Event;
import com.projects.eventticket.eventticket.domain.requests.CreateEventRequest;
import com.projects.eventticket.eventticket.domain.requests.UpdateEventRequest;
import com.projects.eventticket.eventticket.mappers.EventsMapper;
import com.projects.eventticket.eventticket.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.projects.eventticket.eventticket.utils.JwtUtil.parsUserId;

@RestController
@RequestMapping(path = "/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventsMapper eventsMapper;
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<CreateEventResponseDto> createEvent(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateEventRequestDto createEventRequestDto
            ){
        CreateEventRequest createEventRequest =  eventsMapper.fromDto(createEventRequestDto);

        Event createdEvent = eventService.createEvent(parsUserId(jwt),createEventRequest);
        CreateEventResponseDto createEventResponseDto = eventsMapper.toDto(createdEvent);

        return new ResponseEntity<>(createEventResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ListEventResponseDto>> listEvents(
        @AuthenticationPrincipal Jwt jwt,Pageable pageable

    ){
       Page<Event> events = eventService.listEventsForOrganizer(parsUserId(jwt),pageable);

       return ResponseEntity.ok(
               events.map(eventsMapper::toListEventResponseDto)
       );
    }

    @GetMapping(path = "/{eventId}")
    public ResponseEntity<GetEventDetailsResponseDto> getEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId
    ){
        return eventService.getEventForOrganizer(parsUserId(jwt),eventId)
                .map(eventsMapper::toGetEventDetailsResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{eventId}")
    public ResponseEntity<UpdateEventResponseDto> updateEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId,
            @Valid @RequestBody UpdateEventRequestDto updateEventRequestDto
    ){
        UpdateEventRequest updateEventRequest =  eventsMapper.fromDto(updateEventRequestDto);

        Event updatedEvent = eventService.updateEventForOrganizer(
                parsUserId(jwt),
                eventId,
                updateEventRequest
        );

        UpdateEventResponseDto updateEventResponseDto = eventsMapper.toUpdateEventResponseDto(updatedEvent);

        return  ResponseEntity.ok(updateEventResponseDto);
    }

    @DeleteMapping(path= "/{eventId}")
    public ResponseEntity<Void> deleteEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId
    ){
        eventService.deleteEventForOrganizer(
                parsUserId(jwt),
                eventId
        );

        return ResponseEntity.noContent().build();
    }

}
