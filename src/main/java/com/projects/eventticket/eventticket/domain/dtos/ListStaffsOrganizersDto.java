package com.projects.eventticket.eventticket.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListStaffsOrganizersDto {
    private UUID id;

    private String companyName;

    private String displayName;

}
