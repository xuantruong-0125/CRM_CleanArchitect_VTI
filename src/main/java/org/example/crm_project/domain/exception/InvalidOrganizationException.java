package org.example.crm_project.domain.exception;

public class InvalidOrganizationException extends RuntimeException {
    public InvalidOrganizationException(String message) {
        super(message);
    }
}