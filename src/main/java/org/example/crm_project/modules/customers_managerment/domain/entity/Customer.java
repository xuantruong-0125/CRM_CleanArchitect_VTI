package org.example.crm_project.modules.customers_managerment.domain.entity;

import org.example.crm_project.modules.customers_managerment.domain.constant.CustomerStatus;
import org.example.crm_project.modules.customers_managerment.domain.constant.CustomerTier;
import org.example.crm_project.modules.customers_managerment.domain.constant.CustomerType;

import org.example.crm_project.modules.customers_managerment.domain.exception.InvalidCustomerException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Domain Entity: Customer (Aggregate Root)
 * Đại diện cho khách hàng B2B hoặc B2C
 */
public class Customer {
    private Long id;
    private Long parentId;
    private String customerCode;
    private CustomerType type;
    private String name;
    private String shortName;
    private String taxCode;
    private String phone;
    private String email;
    private String fax;
    private LocalDate establishedDate;
    private String description;
    private Long sourceId;
    private CustomerStatus status;
    private CustomerTier tier;
    private Long assignedTo;
    private Long createdBy;
    private Long updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    // Relationships
    private List<CustomerAddress> addresses = new ArrayList<>();
    private List<Feedback> feedbacks = new ArrayList<>();

    // Constructor
    public Customer() {
    }

    public Customer(Long id, CustomerType type, String name, String phone, String email) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }

    // ===== BUSINESS LOGIC & DOMAIN BEHAVIORS =====

    public void generateCustomerCode() {
        if (this.customerCode == null) {
            this.customerCode = "CUS-" + System.currentTimeMillis() + 
                java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
    }

    public void validate() {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidCustomerException("Tên khách hàng là bắt buộc");
        }
        if (email != null && !email.trim().isEmpty() && !email.contains("@")) {
            throw new InvalidCustomerException("Email không đúng định dạng");
        }
    }

    public void initializeCreation() {
        generateCustomerCode();
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = CustomerStatus.ACTIVE;
        }
        if (this.tier == null) {
            this.tier = CustomerTier.SILVER;
        }
        validate();
    }

    public void changeStatus(CustomerStatus newStatus) {
        if (newStatus == null) {
            throw new InvalidCustomerException("Trạng thái không được để trống");
        }
        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();
    }

    public void changeTier(CustomerTier newTier) {
        if (newTier == null) {
            throw new InvalidCustomerException("Phân hạng không được để trống");
        }
        this.tier = newTier;
        this.updatedAt = LocalDateTime.now();
    }

    public void assignToUser(Long userId) {
        this.assignedTo = userId;
        this.updatedAt = LocalDateTime.now();
    }

    // ===== Getters and Setters =====
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public CustomerType getType() {
        return type;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public LocalDate getEstablishedDate() {
        return establishedDate;
    }

    public void setEstablishedDate(LocalDate establishedDate) {
        this.establishedDate = establishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }

    public CustomerTier getTier() {
        return tier;
    }

    public void setTier(CustomerTier tier) {
        this.tier = tier;
    }

    public Long getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Long assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public List<CustomerAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<CustomerAddress> addresses) {
        this.addresses = addresses;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
}
