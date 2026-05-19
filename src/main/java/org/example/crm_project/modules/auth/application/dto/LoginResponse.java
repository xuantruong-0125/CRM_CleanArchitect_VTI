package org.example.crm_project.modules.auth.application.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Set;

@JsonPropertyOrder({
        "accessToken",
        "refreshToken",
        "userId",
        "username",
        "fullName",
        "roles",
        "scope"
})

public class LoginResponse {

    private String accessToken;
    private String refreshToken;
    private Long userId;

    private String username;
    private Set<String> roles;
    private String fullName;
    private String scope;

    public LoginResponse(Long id, String accessToken, String refreshToken,
                         String username, String fullName, Set<String> roles, String scope) {
        this.userId = id;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.username = username;
        this.roles = roles;
        this.fullName = fullName;
        this.scope = scope;
    }

    public Long getUserId() {
        return userId;
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