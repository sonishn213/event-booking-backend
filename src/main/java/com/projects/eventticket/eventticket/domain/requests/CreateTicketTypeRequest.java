package com.projects.eventticket.eventticket.domain.requests;

import com.projects.eventticket.eventticket.domain.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTicketTypeRequest {
    private String name;
    private Double price;
    private String description;
    private Integer totalAvailable;

}
