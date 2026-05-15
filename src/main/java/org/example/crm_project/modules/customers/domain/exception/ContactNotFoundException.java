package org.example.crm_project.modules.customers.domain.exception;

/**
 * Exception khi không tìm thấy người liên hệ
 */
public class ContactNotFoundException extends RuntimeException {
    private final Long contactId;

    public ContactNotFoundException(Long contactId) {
        super("Người liên hệ với ID: " + contactId + " không tồn tại");
        this.contactId = contactId;
    }

    public Long getContactId() {
        return contactId;
    }
}
