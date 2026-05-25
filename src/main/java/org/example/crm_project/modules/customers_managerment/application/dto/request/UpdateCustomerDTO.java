package org.example.crm_project.modules.customers_managerment.application.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO: UpdateCustomerDTO
 * Dùng để cập nhật thông tin khách hàng
 */
@Data
public class UpdateCustomerDTO {
    private String name;
    private String shortName;
    private String phone;
    @Email(message = "Email không hợp lệ")
    private String email;
    private String fax;
    private String description;
    private LocalDate establishedDate;
    private Long sourceId;
    private Long statusId;
    private Long tierId;
    private Long assignedTo;
}
