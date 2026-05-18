package org.example.crm_project.modules.customers.domain.constant;

public enum CustomerStatus {
    ACTIVE(1L, "Đang chăm sóc"),
    STOP_CARING(2L, "Ngừng chăm sóc"),
    BLACKLIST(3L, "Khách hàng xấu (Blacklist)"),
    OTHER(4L, "Khác");

    private final Long id;
    private final String name;

    CustomerStatus(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static CustomerStatus fromId(Long id) {
        for (CustomerStatus status : CustomerStatus.values()) {
            if (status.id.equals(id)) {
                return status;
            }
        }
        return null;
    }
}
