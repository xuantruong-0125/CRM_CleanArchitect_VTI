package org.example.crm_project.modules.system_managerment.domain.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("User not found with id: " + id);
    }
}
