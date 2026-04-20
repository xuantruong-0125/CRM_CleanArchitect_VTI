package org.example.crm_project.modules.leads.domain.exception;

public class InvalidLeadException extends RuntimeException {

    public InvalidLeadException(String message) {
        super(message);
    }
}