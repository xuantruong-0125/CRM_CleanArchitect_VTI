package org.example.crm_project.modules.system_managerment.domain.entity;

import org.example.crm_project.modules.system_managerment.domain.constant.OrganizationType;

public class Organization {

    private Long id;
    private String name;
    private Long parentId;
    private OrganizationType type;

    // ===== Constructor create =====
    public Organization(String name, Long parentId, OrganizationType type) {
        validateName(name);
        this.name = name;
        this.parentId = parentId;
        this.type = type;
    }

    // ===== Constructor load từ DB =====
    public Organization(Long id, String name, Long parentId, OrganizationType type) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.type = type;
    }

    // ===== Getter =====
    public Long getId() { return id; }
    public String getName() { return name; }
    public Long getParentId() { return parentId; }
    public OrganizationType getType() { return type; }

    // ===== Business logic =====
    public void changeName(String newName) {
        validateName(newName);
        this.name = newName;
    }

    public void changeParent(Long newParentId) {
        if (this.id != null && this.id.equals(newParentId)) {
            throw new IllegalArgumentException("Parent cannot be itself");
        }
        this.parentId = newParentId;
    }

    public void changeType(OrganizationType newType) {
        this.type = newType;
    }

    // ===== Validate =====
    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Organization name cannot be empty");
        }
    }
}