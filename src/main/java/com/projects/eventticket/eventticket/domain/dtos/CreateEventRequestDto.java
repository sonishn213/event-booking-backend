package com.projects.eventticket.eventticket.domain.dtos;

import com.projects.eventticket.eventticket.domain.enums.EventStatusEnum;
import com.projects.eventticket.eventticket.domain.requests.CreateTicketTypeRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventRequestDto {

    @NotBlank(message = "Event Name is required")
    private String name;

    private LocalDateTime start;

    private LocalDateTime end;

    @NotBlank(message = "Venue information is required")
    private String venue;

    private LocalDateTime salesStart;

    private LocalDateTime salesEnd;

    @NotNull(message = "Event status is required")
    private EventStatusEnum status;

    @NotEmpty(message = "Atleast one ticket type is required")
    @Valid
    private List<CreateTicketTypeRequestDto> ticketTypes;

}
