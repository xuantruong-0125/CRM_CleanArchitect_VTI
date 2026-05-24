package org.example.crm_project.modules.leads_managerment.domain.exception;

public class LeadNotFoundException extends RuntimeException {

    public LeadNotFoundException(Long id) {
        super("Lead not found with id: " + id);
    }
}
