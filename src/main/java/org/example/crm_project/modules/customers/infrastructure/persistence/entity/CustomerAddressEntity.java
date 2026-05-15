package org.example.crm_project.modules.customers.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Persistence Entity: CustomerAddressEntity
 * Represents customer addresses
 */
@Entity
@Table(name = "customer_addresses", indexes = {
    @Index(name = "idx_address_customer", columnList = "customer_id")
})
public class CustomerAddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    
    @Column(name = "address_type", length = 10)
    private String addressType;
    
    @Column(name = "full_address", length = 200, nullable = false)
    private String fullAddress;
    
    @Column(name = "province_id")
    private Integer provinceId;
    
    @Column(name = "is_primary")
    private Boolean isPrimary = false;
    
    @Transient
    private LocalDateTime createdAt;
    
    @Transient
    private LocalDateTime updatedAt;
    
    @Transient
    private Long createdBy;
    
    @Transient
    private Long updatedBy;
    
    @Transient
    private LocalDateTime deletedAt;
    
    @PrePersist
    protected void onCreate() {
        if (isPrimary == null) isPrimary = false;
    }
    
    @PreUpdate
    protected void onUpdate() {
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
    
    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }
    
    public Long getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(Long updatedBy) { this.updatedBy = updatedBy; }
    
    public LocalDateTime getDeletedAt() { return deletedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }
}
