package org.example.crm_project.modules.auth.domain.exception;

import org.example.crm_project.modules.auth.domain.constant.AuthErrorCode;

public class InvalidCredentialsException extends AuthException {

    public InvalidCredentialsException() {
        super("Invalid username or password", AuthErrorCode.INVALID_CREDENTIALS);
    }
}