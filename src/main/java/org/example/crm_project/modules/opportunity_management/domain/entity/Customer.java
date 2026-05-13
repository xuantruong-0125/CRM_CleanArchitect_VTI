package org.example.crm_project.modules.opportunity_management.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Domain Entity – Customer (khách hàng, tham chiếu ngoài module).
 * Chỉ lưu các field cần thiết để hiển thị trong module Opportunity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    private Integer id;
    private String name;
    private String shortName;
    private String phone;
    private String email;
}
