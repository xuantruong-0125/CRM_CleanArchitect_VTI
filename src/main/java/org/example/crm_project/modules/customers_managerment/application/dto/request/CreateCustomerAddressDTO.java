package org.example.crm_project.modules.customers_managerment.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO: CreateCustomerAddressDTO
 * Create/Update DTO for CustomerAddress
 */
@Data
public class CreateCustomerAddressDTO {
    @NotNull(message = "ID khách hàng không được để trống")
    private Long customerId;

    private String addressType;
    
    @NotBlank(message = "Địa chỉ không được để trống")
    private String fullAddress;

    private Integer provinceId;
    private Boolean isPrimary = false;
}
