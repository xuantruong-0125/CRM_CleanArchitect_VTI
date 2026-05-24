package org.example.crm_project.modules.customers_managerment.domain.constant;

public enum TaskPriority {
    LOW("LOW", "Thấp"),
    NORMAL("NORMAL", "Bình thường"),
    HIGH("HIGH", "Cao"),
    URGENT("URGENT", "Khẩn cấp");

    private final String code;
    private final String name;

    TaskPriority(String code, String name) {
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
