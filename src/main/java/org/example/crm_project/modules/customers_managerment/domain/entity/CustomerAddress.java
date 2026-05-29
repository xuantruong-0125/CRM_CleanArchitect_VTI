package org.example.crm_project.modules.customers_managerment.domain.entity;

import java.time.LocalDateTime;

/**
 * Domain Entity: CustomerAddress
 * Đại diện cho địa chỉ khách hàng
 */
public class CustomerAddress {
    private Long id;
    private Long customerId;
    private String addressType;
    private String fullAddress;
    private Integer provinceId;
    private Boolean isPrimary;

    private Customer customer;

    // Constructor
    public CustomerAddress() {
    }

    public CustomerAddress(Long customerId, String addressType, String fullAddress) {
        this.customerId = customerId;
        this.addressType = addressType;
        this.fullAddress = fullAddress;
        this.isPrimary = false;
    }

    // ===== DOMAIN BEHAVIORS =====

    public void markAsPrimary() {
        this.isPrimary = true;
    }

    public void demoteFromPrimary() {
        this.isPrimary = false;
    }

    public void updateInfo(String addressType, String fullAddress, Integer provinceId, Boolean isPrimary) {
        this.addressType = addressType;
        this.fullAddress = fullAddress;
        this.provinceId = provinceId;
        if (isPrimary != null) {
            this.isPrimary = isPrimary;
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean primary) {
        isPrimary = primary;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
