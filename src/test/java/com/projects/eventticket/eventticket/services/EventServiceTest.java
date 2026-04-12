package com.projects.eventticket.eventticket.services;

import com.projects.eventticket.eventticket.domain.requests.CreateEventRequest;
import com.projects.eventticket.eventticket.exception.UserNotFoundException;
import com.projects.eventticket.eventticket.repository.EventRepository;
import com.projects.eventticket.eventticket.repository.UserRepository;
import com.projects.eventticket.eventticket.services.impl.EventServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    EventServiceImpl eventService;

    @Test
    void shouldThrowException_whenUserNotFound(){
        //Arrange
        UUID organizerId = UUID.randomUUID();

        CreateEventRequest createEventRequest = new CreateEventRequest();

        //Act and assert
        assertThrows(
            UserNotFoundException.class,
            ()-> eventService.createEvent(organizerId,createEventRequest)
        );
    }
}
