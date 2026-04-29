package org.example.crm_project.modules.contacts_managerment.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    private Long statusId;
    private Long tierId;
    private Long assignedTo;
    private Long createdBy;
    private Long updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    
    private List<Contact> contacts;

    public Customer() {
    }

    public Customer(Long id, Long parentId, String customerCode, CustomerType type, String name, String shortName, String taxCode, String phone, String email, String fax, LocalDate establishedDate, String description, Long sourceId, Long statusId, Long tierId, Long assignedTo, Long createdBy, Long updatedBy, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.parentId = parentId;
        this.customerCode = customerCode;
        this.type = type;
        this.name = name;
        this.shortName = shortName;
        this.taxCode = taxCode;
        this.phone = phone;
        this.email = email;
        this.fax = fax;
        this.establishedDate = establishedDate;
        this.description = description;
        this.sourceId = sourceId;
        this.statusId = statusId;
        this.tierId = tierId;
        this.assignedTo = assignedTo;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }

    public String getCustomerCode() { return customerCode; }
    public void setCustomerCode(String customerCode) { this.customerCode = customerCode; }

    public CustomerType getType() { return type; }
    public void setType(CustomerType type) { this.type = type; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getShortName() { return shortName; }
    public void setShortName(String shortName) { this.shortName = shortName; }

    public String getTaxCode() { return taxCode; }
    public void setTaxCode(String taxCode) { this.taxCode = taxCode; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFax() { return fax; }
    public void setFax(String fax) { this.fax = fax; }

    public LocalDate getEstablishedDate() { return establishedDate; }
    public void setEstablishedDate(LocalDate establishedDate) { this.establishedDate = establishedDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getSourceId() { return sourceId; }
    public void setSourceId(Long sourceId) { this.sourceId = sourceId; }

    public Long getStatusId() { return statusId; }
    public void setStatusId(Long statusId) { this.statusId = statusId; }

    public Long getTierId() { return tierId; }
    public void setTierId(Long tierId) { this.tierId = tierId; }

    public Long getAssignedTo() { return assignedTo; }
    public void setAssignedTo(Long assignedTo) { this.assignedTo = assignedTo; }

    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }

    public Long getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(Long updatedBy) { this.updatedBy = updatedBy; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDateTime getDeletedAt() { return deletedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }

    public List<Contact> getContacts() { return contacts; }
    public void setContacts(List<Contact> contacts) { this.contacts = contacts; }
}
