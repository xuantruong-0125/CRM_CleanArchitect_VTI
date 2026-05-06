package org.example.crm_project.modules.system_managerment.domain.exception;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String email) {
        super("Email đã tồn tại: " + email);
    }
}