package org.example.crm_project.domain.exception;

public class OrganizationNotFoundException extends RuntimeException {
    public OrganizationNotFoundException(Long id) {
        super("Organization not found with id: " + id);
    }
}
