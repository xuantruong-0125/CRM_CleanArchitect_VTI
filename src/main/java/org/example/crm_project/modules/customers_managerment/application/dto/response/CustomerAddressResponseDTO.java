package org.example.crm_project.modules.customers_managerment.application.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO: CustomerAddressResponseDTO
 * Response DTO for CustomerAddress entity
 */
@Data
public class CustomerAddressResponseDTO {
    private Long id;
    private Long customerId;
    private String addressType;
    private String fullAddress;
    private Integer provinceId;
    private Boolean isPrimary;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CustomerAddressResponseDTO() {}

    public CustomerAddressResponseDTO(Long id, Long customerId, String fullAddress) {
        this.id = id;
        this.customerId = customerId;
        this.fullAddress = fullAddress;
    }
}
