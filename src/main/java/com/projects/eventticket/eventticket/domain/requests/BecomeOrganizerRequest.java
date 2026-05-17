package com.projects.eventticket.eventticket.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BecomeOrganizerRequest {
    private String companyName;
    private String displayName;
    private String companyEmail;
    private String companyPhone;
    private String gstNumber;
    private String address;
}
