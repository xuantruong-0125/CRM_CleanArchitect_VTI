package org.example.crm_project.modules.customers.domain.constant;

public enum TaskStatus {
    NOT_STARTED("NOT_STARTED", "Chưa bắt đầu"),
    IN_PROGRESS("IN_PROGRESS", "Đang thực hiện"),
    COMPLETED("COMPLETED", "Hoàn thành"),
    CANCELLED("CANCELLED", "Hủy bỏ");

    private final String code;
    private final String name;

    TaskStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
