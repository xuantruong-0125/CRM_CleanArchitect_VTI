package org.example.crm_project.modules.customers_managerment.domain.exception;

/**
 * Exception: EntityNotFoundException
 * Thrown when an entity is not found in the database
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
