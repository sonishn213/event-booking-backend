package com.projects.eventticket.eventticket.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListTicketTicketTypeResponseDto {
    private UUID Id;
    private String name;
    private Double price;
    private String eventName;
    private LocalDateTime eventStart;
    private LocalDateTime eventEnd;
    private String eventVenue;
}
