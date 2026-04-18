package org.example.crm_project.modules.system_managerment.domain.exception;

public class OrganizationCycleException extends RuntimeException {
    public OrganizationCycleException() {
        super("Organization hierarchy cannot contain cycles");
    }
}