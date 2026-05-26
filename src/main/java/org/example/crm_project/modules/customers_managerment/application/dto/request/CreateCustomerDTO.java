package org.example.crm_project.modules.customers_managerment.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO: CreateCustomerDTO
 * Dùng để nhận dữ liệu tạo khách hàng mới
 */
@Data
public class CreateCustomerDTO {
    @NotBlank(message = "Tên khách hàng không được để trống")
    private String name;

    @NotBlank(message = "Loại khách hàng không được để trống")
    private String type;  // B2B, B2C

    @NotBlank(message = "Số điện thoại không được để trống")
    private String phone;

    @Email(message = "Email không hợp lệ")
    private String email;

    private String taxCode;
    private String shortName;
    private String fax;
    private String description;
    private LocalDate establishedDate;
    private Long sourceId;
    private Long statusId;
    private Long tierId;
    private Long assignedTo;
}
