package com.projects.eventticket.eventticket.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BecomeOrganizerRequestDto {
    @NotBlank(message = "Company Name is required")
    private String companyName;

    @NotBlank(message = "Display Name is required")
    private String displayName;

    @NotBlank(message = "Company Email is required")
    private String companyEmail;

    @NotBlank(message = "Phone number is required")
    private String companyPhone;

    @NotBlank(message = "Gst number is required")
    private String gstNumber;

    @NotBlank(message = "Address is required")
    private String address;

}
