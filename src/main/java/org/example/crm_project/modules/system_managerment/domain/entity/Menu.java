package org.example.crm_project.modules.system_managerment.domain.entity;

import java.time.LocalDateTime;

public class Menu {

    private Long id;
    private String name;
    private String code;      // 🔥 QUAN TRỌNG (dùng cho permission)
    private Long parentId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ===== CONSTRUCTOR =====

    public Menu(String name, String code, Long parentId) {
        validateName(name);
        validateCode(code);

        this.name = name;
        this.code = code;
        this.parentId = parentId;
        this.createdAt = LocalDateTime.now();
    }

    public Menu(Long id, String name, String code, Long parentId) {
        validateName(name);
        validateCode(code);

        this.id = id;
        this.name = name;
        this.code = code;
        this.parentId = parentId;

    }

    // ===== GETTER =====

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCode() { return code; }
    public Long getParentId() { return parentId; }

    // ===== BUSINESS =====

    public void changeName(String newName) {
        validateName(newName);
        this.name = newName;
        this.updatedAt = LocalDateTime.now();
    }

    public void changeCode(String newCode) {
        validateCode(newCode);
        this.code = newCode;
        this.updatedAt = LocalDateTime.now();
    }

    public void changeParent(Long newParentId) {
        this.parentId = newParentId;
        this.updatedAt = LocalDateTime.now();
    }

    // ===== VALIDATION =====

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Menu name cannot be empty");
        }
    }

    private void validateCode(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Menu code cannot be empty");
        }

        if (!code.matches("^[A-Z0-9_]+$")) {
            throw new IllegalArgumentException("Menu code must be UPPER_CASE_WITH_UNDERSCORE");
        }
    }
}