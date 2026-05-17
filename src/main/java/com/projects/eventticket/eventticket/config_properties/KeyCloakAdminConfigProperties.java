package com.projects.eventticket.eventticket.config_properties;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "keycloak.admin")
public record KeyCloakAdminConfigProperties(
    @NotBlank
    String server_url,
    @NotBlank
    String realm,
    @NotBlank
    String client_id,
    @NotBlank
    String username,
    @NotBlank
    String password
) {
}