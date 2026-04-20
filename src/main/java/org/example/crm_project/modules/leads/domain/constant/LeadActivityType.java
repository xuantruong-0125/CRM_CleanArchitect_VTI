package org.example.crm_project.modules.leads.domain.constant;

import java.util.Arrays;

public enum LeadActivityType {
    EMAIL(1),
    CALL(2),
    MEETING(3);

    private final int code;

    LeadActivityType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static LeadActivityType fromCode(Integer code) {
        if (code == null) {
            return null;
        }

        return Arrays.stream(values())
                .filter(type -> type.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported activityType code: " + code));
    }

    public static LeadActivityType fromName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }

        return Arrays.stream(values())
                .filter(type -> type.name().equalsIgnoreCase(name.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported activityType: " + name));
    }
}