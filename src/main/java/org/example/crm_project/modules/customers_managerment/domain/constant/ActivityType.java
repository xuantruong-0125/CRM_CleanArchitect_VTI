package org.example.crm_project.modules.customers_managerment.domain.constant;

public enum ActivityType {
    EMAIL(1, "Email"),
    CALL(2, "Cuộc gọi"),
    MEETING(3, "Gặp mặt"),
    NOTE(4, "Ghi chú"),
    OTHER(5, "Khác");

    private final int code;
    private final String name;

    ActivityType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static ActivityType fromCode(int code) {
        for (ActivityType type : ActivityType.values()) {
            if (type.code == code) {
                return type;
            }
        }
        return OTHER;
    }
}
