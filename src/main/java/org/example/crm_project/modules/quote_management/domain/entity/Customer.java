package org.example.crm_project.modules.quote_management.domain.entity;

import org.example.crm_project.modules.quote_management.domain.constant.CustomerType;

/**
 * Domain entity cho khách hàng.
 * POJO thuần túy - không có JPA annotation.
 */
public class Customer {

    private Integer id;
    private String customerCode;
    private String name;
    private String shortName;
    private CustomerType type;
    private String phone;
    private String email;

    // ===== Constructor load từ DB =====
    public Customer(Integer id, String customerCode, String name, String shortName,
                    CustomerType type, String phone, String email) {
        this.id = id;
        this.customerCode = customerCode;
        this.name = name;
        this.shortName = shortName;
        this.type = type;
        this.phone = phone;
        this.email = email;
    }

    // ===== Getter =====
    public Integer getId() { return id; }
    public String getCustomerCode() { return customerCode; }
    public String getName() { return name; }
    public String getShortName() { return shortName; }
    public CustomerType getType() { return type; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
}
