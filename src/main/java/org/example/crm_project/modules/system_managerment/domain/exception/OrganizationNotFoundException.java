package org.example.crm_project.modules.system_managerment.domain.exception;

public class OrganizationNotFoundException extends RuntimeException {
    public OrganizationNotFoundException(Long id) {
        super("Organization not found with id: " + id);
    }
}
