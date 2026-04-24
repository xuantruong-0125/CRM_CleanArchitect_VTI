package org.example.crm_project.modules.activity_management.domain.constant;

public enum ActivityType {
    CALL(1), EMAIL(2), EMAIL_QUOTE(6), // Thêm số 6 vào đây
    EMAIL_TRANS(7), MEETING(3), NOTE(4), SYSTEM_LOG(5);

    private final int value;

    ActivityType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
