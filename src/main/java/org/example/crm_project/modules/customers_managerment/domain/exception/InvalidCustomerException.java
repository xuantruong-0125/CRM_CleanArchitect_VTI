package org.example.crm_project.modules.customers_managerment.domain.exception;

/**
 * Exception khi dữ liệu khách hàng không hợp lệ
 */
public class InvalidCustomerException extends RuntimeException {
    public InvalidCustomerException(String message) {
        super(message);
    }

    public InvalidCustomerException(String message, Throwable cause) {
        super(message, cause);
    }
}
