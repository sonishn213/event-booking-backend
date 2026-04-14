package com.projects.eventticket.eventticket.services;

import com.projects.eventticket.eventticket.domain.entity.Event;
import com.projects.eventticket.eventticket.domain.requests.CreateEventRequest;
import com.projects.eventticket.eventticket.domain.requests.UpdateEventRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface EventService {
    Event createEvent(UUID organizerId, CreateEventRequest event);
    Page<Event> listEventsForOrganizer(UUID organizer_id, Pageable pagable);
    Optional<Event> getEventForOrganizer(UUID organizer_id,UUID eventId);
    Event updateEventForOrganizer(UUID organizerId, UUID eventId, UpdateEventRequest updateEventRequest);
    void deleteEventForOrganizer(UUID organizerId, UUID eventId);
    Page<Event> listPublishedEvents(Pageable pageable);
    Page<Event> searchPublishedEvents(String query,Pageable pageable);
    Event getPublishedEvent(UUID eventId);
}
