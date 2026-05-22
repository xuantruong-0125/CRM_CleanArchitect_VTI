package org.example.crm_project.modules.auth.domain.constant;

public final class AuthErrorCode {

    public static final String USER_NOT_FOUND = "AUTH_001";
    public static final String INVALID_CREDENTIALS = "AUTH_002";
    public static final String USER_DISABLED = "AUTH_003";

    private AuthErrorCode() {
    }
}