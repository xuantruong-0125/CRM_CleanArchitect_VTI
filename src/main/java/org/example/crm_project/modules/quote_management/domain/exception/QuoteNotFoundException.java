package org.example.crm_project.modules.quote_management.domain.exception;

public class QuoteNotFoundException extends RuntimeException {
    public QuoteNotFoundException(Integer id) {
        super("Không tìm thấy báo giá với ID: " + id);
    }
}
