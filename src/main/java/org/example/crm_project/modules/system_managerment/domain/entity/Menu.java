package org.example.crm_project.modules.system_managerment.domain.entity;

public class Menu {

    private Long id;
    private String name;
    private Long parentId;

    // ===== CONSTRUCTOR =====
    public Menu(Long id, String name, Long parentId) {
        validateName(name);
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public Menu(String name, Long parentId) {
        validateName(name);
        this.name = name;
        this.parentId = parentId;
    }

    // ===== GETTER =====
    public Long getId() { return id; }
    public String getName() { return name; }
    public Long getParentId() { return parentId; }

    // ===== BUSINESS LOGIC =====
    public void changeName(String newName) {
        validateName(newName);
        this.name = newName;
    }

    public void changeParent(Long newParentId) {
        this.parentId = newParentId;
    }

    // ===== VALIDATION =====
    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Menu name cannot be empty");
        }
    }
}