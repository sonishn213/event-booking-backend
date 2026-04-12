package com.projects.eventticket.eventticket.utils;

import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public final class JwtUtil {
    private JwtUtil(){

    }

    public static UUID parsUserId(Jwt jwt){
        return UUID.fromString(jwt.getSubject());
    }

}
