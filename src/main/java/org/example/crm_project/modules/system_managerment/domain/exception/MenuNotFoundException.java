package org.example.crm_project.modules.system_managerment.domain.exception;

public class MenuNotFoundException extends RuntimeException {
    public MenuNotFoundException(Long id) {
        super("Menu not found with id: " + id);
    }
}