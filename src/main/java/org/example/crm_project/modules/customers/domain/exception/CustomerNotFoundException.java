package org.example.crm_project.modules.customers.domain.exception;

/**
 * Exception khi không tìm thấy khách hàng
 */
public class CustomerNotFoundException extends RuntimeException {
    private final Long customerId;

    public CustomerNotFoundException(Long customerId) {
        super("Khách hàng với ID: " + customerId + " không tồn tại");
        this.customerId = customerId;
    }

    public CustomerNotFoundException(String message) {
        super(message);
        this.customerId = null;
    }

    public Long getCustomerId() {
        return customerId;
    }
}
