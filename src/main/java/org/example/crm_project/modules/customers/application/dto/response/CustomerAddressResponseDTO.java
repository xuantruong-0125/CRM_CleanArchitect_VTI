package org.example.crm_project.modules.customers.application.dto.response;

import java.time.LocalDateTime;

/**
 * DTO: CustomerAddressResponseDTO
 * Response DTO for CustomerAddress entity
 */
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

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
