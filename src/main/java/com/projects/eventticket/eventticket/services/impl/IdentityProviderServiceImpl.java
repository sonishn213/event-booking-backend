package com.projects.eventticket.eventticket.services.impl;

import com.projects.eventticket.eventticket.domain.enums.UserRoleEnum;
import com.projects.eventticket.eventticket.services.IdentityProviderService;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IdentityProviderServiceImpl implements IdentityProviderService {

    private final RealmResource keycloakRealm;

    @Override
    public void assignRoleOrganizer(String userId) {
        UserResource user  = keycloakRealm.users().get(userId);

        RoleResource role = keycloakRealm.roles().get(
                UserRoleEnum.ROLE_ORGANIZER.toString().toLowerCase()
        );

        List<RoleRepresentation> roleRepresentations = new ArrayList<>(
                List.of(role.toRepresentation())
        );

        user.roles().realmLevel().add(roleRepresentations);
    }
}
