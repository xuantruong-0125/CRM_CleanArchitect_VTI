package org.example.crm_project.modules.activity_management.domain.constant;

public enum ActivityStatus {
    PLANNED(0), // Đổi thành 0 cho khớp với DB
    COMPLETED(1), // Đổi thành 1
    CANCELED(2); // Nếu có hủy thì để 2, không thì bỏ qua

    private final int value;

    ActivityStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}