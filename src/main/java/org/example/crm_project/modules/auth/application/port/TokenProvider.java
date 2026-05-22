package org.example.crm_project.modules.auth.application.port;

import org.example.crm_project.modules.auth.domain.entity.AuthUser;

public interface TokenProvider {

    String generateAccessToken(AuthUser user);

    String generateRefreshToken(AuthUser user);

    String getUsernameFromToken(String token);

    boolean validateToken(String token);
}