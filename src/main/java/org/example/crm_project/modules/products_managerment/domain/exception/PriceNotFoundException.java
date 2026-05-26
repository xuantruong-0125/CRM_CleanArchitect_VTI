package org.example.crm_project.modules.products_managerment.domain.exception;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(Long id) {
        super("Price not found with id: " + id);
    }
}
