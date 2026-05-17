package com.projects.eventticket.eventticket.domain.dtos;

import com.projects.eventticket.eventticket.domain.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListStaffResponseDto {
    private UUID id;
    private String name;
    private String email;
    private List<Event> staffingEvents = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
