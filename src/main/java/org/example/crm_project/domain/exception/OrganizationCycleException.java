package org.example.crm_project.domain.exception;

public class OrganizationCycleException extends RuntimeException {
    public OrganizationCycleException() {
        super("Organization hierarchy cannot contain cycles");
    }
}