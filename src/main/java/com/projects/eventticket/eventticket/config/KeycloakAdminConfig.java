package com.projects.eventticket.eventticket.config;

import com.projects.eventticket.eventticket.config_properties.KeyCloakAdminConfigProperties;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakAdminConfig {
    @Bean
    public Keycloak keycloak(KeyCloakAdminConfigProperties props) {

        return KeycloakBuilder.builder()
                .serverUrl(props.server_url())
                .realm("master")
                .clientId(props.client_id())
                .username(props.username())
                .password(props.password())
                .grantType(OAuth2Constants.PASSWORD)
                .build();

    }

    @Bean
    public RealmResource keycloakRealm(
            Keycloak keycloak,
            KeyCloakAdminConfigProperties props
    ){
        return keycloak.realm(props.realm());
    }

}
