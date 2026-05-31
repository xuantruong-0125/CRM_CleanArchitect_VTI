package org.example.crm_project.modules.customers_managerment.application.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO: CustomerResponseDTO
 * Dùng để trả về dữ liệu khách hàng trong API response
 */
@Data
public class CustomerResponseDTO {
    private Long id;
    private String customerCode;
    private String type;
    private String name;
    private String shortName;
    private String taxCode;
    private String phone;
    private String email;
    private String fax;
    private String description;
    private Long sourceId;
    private String statusName;
    private String tierName;
    private Long assignedTo;
    private LocalDate establishedDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public CustomerResponseDTO() {
    }

    public CustomerResponseDTO(Long id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}
