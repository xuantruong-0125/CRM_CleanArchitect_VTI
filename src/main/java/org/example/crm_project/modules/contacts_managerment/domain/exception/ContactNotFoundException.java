package org.example.crm_project.modules.contacts_managerment.domain.exception;

public class ContactNotFoundException extends RuntimeException {
    public ContactNotFoundException(String message) {
        super(message);
    }
}
