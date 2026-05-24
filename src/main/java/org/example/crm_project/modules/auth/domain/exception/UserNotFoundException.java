package org.example.crm_project.modules.auth.domain.exception;

import org.example.crm_project.modules.auth.domain.constant.AuthErrorCode;

public class UserNotFoundException extends AuthException {

    public UserNotFoundException() {
        super("User not found", AuthErrorCode.USER_NOT_FOUND);
    }
}