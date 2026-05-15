package org.example.crm_project.modules.customers.domain.constant;

public enum CustomerType {
    B2B("B2B", "Khách hàng doanh nghiệp"),
    B2C("B2C", "Khách hàng cá nhân");

    private final String code;
    private final String name;

    CustomerType(String code, String name) {
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
