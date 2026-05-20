package org.example.crm_project.modules.contracts.domain.constant;

public enum ContractStatus {
    DRAFT("Nháp"),
    SIGNED("Đã ký"),
    ACTIVE("Đang hiệu lực"),
    COMPLETED("Hoàn thành"),
    CANCELLED("Hủy");

    private final String displayName;

    ContractStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
