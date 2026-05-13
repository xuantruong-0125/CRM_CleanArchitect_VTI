package org.example.crm_project.modules.customers.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.crm_project.modules.customers.domain.constant.CustomerType;

/**
 * Domain entity cho khách hàng.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Long id;
    private String customerCode;
    private String name;
    private String shortName;
    private CustomerType type;
    private String phone;
    private String email;
}
