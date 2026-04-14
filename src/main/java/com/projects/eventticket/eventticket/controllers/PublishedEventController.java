package com.projects.eventticket.eventticket.controllers;

import com.projects.eventticket.eventticket.domain.dtos.GetPublishedEventDetailsResponseDto;
import com.projects.eventticket.eventticket.domain.dtos.ListPublishedEventResponseDto;
import com.projects.eventticket.eventticket.domain.dtos.PageWrapperDto;
import com.projects.eventticket.eventticket.domain.entity.Event;
import com.projects.eventticket.eventticket.mappers.EventsMapper;
import com.projects.eventticket.eventticket.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/published-events")
@RequiredArgsConstructor
public class PublishedEventController {

    private final EventService eventService;
    private final EventsMapper eventsMapper;

    @GetMapping
    public ResponseEntity<Page<ListPublishedEventResponseDto>> listPublishedEvents(
            @RequestParam(required = false) String q,
            Pageable pageable
    ){
        PageWrapperDto<ListPublishedEventResponseDto> events;

        if(null != q && !q.trim().isEmpty()){
            events = eventService.searchPublishedEvents(q,pageable);
        }else{
            events = eventService.listPublishedEvents(pageable);
        }

        return ResponseEntity.ok(
                events.toPage(pageable)
        );
    }

    @GetMapping(path="/{eventId}")
    public ResponseEntity<GetPublishedEventDetailsResponseDto> getPublishedEventDetails(
            @PathVariable UUID eventId
    ){
        GetPublishedEventDetailsResponseDto event = eventService.getPublishedEvent(eventId);

        if (event == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(event);
    }
}
