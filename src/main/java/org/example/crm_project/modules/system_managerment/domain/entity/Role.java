package org.example.crm_project.modules.system_managerment.domain.entity;

import org.example.crm_project.modules.system_managerment.domain.constant.RoleScope;

public class Role {

    private Long id;
    private String name;
    private String description;
    private RoleScope scope;

    // ===== CONSTRUCTOR =====
    public Role(Long id, String name, String description, RoleScope scope) {
        validate(name, scope);
        this.id = id;
        this.name = name;
        this.description = description;
        this.scope = scope;
    }

    public Role(String name, String description, RoleScope scope) {
        validate(name, scope);
        this.name = name;
        this.description = description;
        this.scope = scope;
    }

    // ===== GETTER =====
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public RoleScope getScope() { return scope; }

    // ===== BUSINESS LOGIC =====
    public void changeName(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Role name cannot be empty");
        }
        this.name = newName;
    }

    public void changeDescription(String newDescription) {
        this.description = newDescription;
    }

    public void changeScope(RoleScope newScope) {
        if (newScope == null) {
            throw new IllegalArgumentException("Scope cannot be null");
        }
        this.scope = newScope;
    }

    // ===== VALIDATION =====
    private void validate(String name, RoleScope scope) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Role name cannot be empty");
        }
        if (scope == null) {
            throw new IllegalArgumentException("Role scope is required");
        }
    }
}