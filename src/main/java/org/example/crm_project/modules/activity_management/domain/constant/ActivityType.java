package org.example.crm_project.modules.activity_management.domain.constant;

public enum ActivityType {
    CALL(1),
    MEETING(2),
    EMAIL(3),
    EMAIL_QUOTE(4),  
    EMAIL_TRANS(5);

    private final int value;

    ActivityType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
