package org.example.crm_project.modules.customers.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO: CreateCustomerAddressDTO
 * Create/Update DTO for CustomerAddress
 */
public class CreateCustomerAddressDTO {
    @NotNull(message = "ID khách hàng không được để trống")
    private Long customerId;

    private String addressType;
    
    @NotBlank(message = "Địa chỉ không được để trống")
    private String fullAddress;

    private Integer provinceId;
    private Boolean isPrimary = false;

    public CreateCustomerAddressDTO() {}

    // Getters & Setters
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getAddressType() { return addressType; }
    public void setAddressType(String addressType) { this.addressType = addressType; }

    public String getFullAddress() { return fullAddress; }
    public void setFullAddress(String fullAddress) { this.fullAddress = fullAddress; }

    public Integer getProvinceId() { return provinceId; }
    public void setProvinceId(Integer provinceId) { this.provinceId = provinceId; }

    public Boolean getIsPrimary() { return isPrimary; }
    public void setIsPrimary(Boolean isPrimary) { this.isPrimary = isPrimary; }
}
