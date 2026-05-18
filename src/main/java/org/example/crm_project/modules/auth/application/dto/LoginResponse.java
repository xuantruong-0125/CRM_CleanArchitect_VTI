package org.example.crm_project.modules.auth.application.dto;

import java.util.Set;

public class LoginResponse {

    private String accessToken;
    private String refreshToken;
    private String username;
    private Set<String> roles;
    private String fullName;
    private String scope;

    public LoginResponse(String accessToken, String refreshToken,
                         String username, String fullName, Set<String> roles, String scope) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.username = username;
        this.roles = roles;
        this.fullName = fullName;
        this.scope = scope;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public String getFullName() {
        return fullName;
    }

    public String getScope() {
        return scope;
    }
}