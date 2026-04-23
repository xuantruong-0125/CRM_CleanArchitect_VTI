package org.example.crm_project.modules.system_managerment.domain.exception;

public class InvalidUserException extends RuntimeException {

    public InvalidUserException(String message) {
        super(message);
    }
}