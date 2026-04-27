package com.projects.eventticket.eventticket.config_properties;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties(prefix = "razorpay")
public record RazorPayConfigProperties(
        @NotBlank
        String key_id,
        @NotBlank
        String key_secret
) {

}
