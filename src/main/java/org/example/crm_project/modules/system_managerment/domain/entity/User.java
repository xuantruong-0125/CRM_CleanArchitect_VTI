package org.example.crm_project.modules.system_managerment.domain.entity;

import org.example.crm_project.modules.system_managerment.domain.constant.UserStatus;
import org.example.crm_project.modules.system_managerment.domain.exception.InvalidUserException;

import java.time.LocalDateTime;

public class User {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String fullName;

    private Long roleId;
    private Long organizationId;

    private UserStatus status;

    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    // ===== CONSTRUCTOR =====
    public User(String username,
                String password,
                String email,
                String fullName,
                Long roleId,
                Long organizationId) {

        if (username == null || username.isBlank()) {
            throw new InvalidUserException("Username is required");
        }

        if (password == null || password.isBlank()) {
            throw new InvalidUserException("Password is required");
        }

        if (email == null || email.isBlank()) {
            throw new InvalidUserException("Email is required");
        }

        if (roleId == null) {
            throw new InvalidUserException("Role is required");
        }

        if (organizationId == null) {
            throw new InvalidUserException("Organization is required");
        }

        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.roleId = roleId;
        this.organizationId = organizationId;
        this.status = UserStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
    }

    // ===== GETTER =====
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }
    public Long getRoleId() { return roleId; }
    public Long getOrganizationId() { return organizationId; }
    public UserStatus getStatus() { return status; }
    public LocalDateTime getLastLogin() { return lastLogin; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public LocalDateTime getDeletedAt() { return deletedAt; }

    // ===== BUSINESS METHODS =====

    public void changeEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new InvalidUserException("Invalid email");
        }
        this.email = email;
        this.updatedAt = LocalDateTime.now();
    }

    public void changeFullName(String fullName) {
        this.fullName = fullName;
        this.updatedAt = LocalDateTime.now();
    }

    public void changeRole(Long roleId) {
        if (roleId == null) {
            throw new InvalidUserException("Role cannot be null");
        }
        this.roleId = roleId;
        this.updatedAt = LocalDateTime.now();
    }

    public void changeOrganization(Long organizationId) {
        if (organizationId == null) {
            throw new InvalidUserException("Organization cannot be null");
        }
        this.organizationId = organizationId;
        this.updatedAt = LocalDateTime.now();
    }

    public void changeStatus(UserStatus status) {
        if (status == null) {
            throw new InvalidUserException("Status invalid");
        }
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public void changePassword(String oldPassword, String newPassword) {

        if (!this.password.equals(oldPassword)) {
            throw new InvalidUserException("Old password incorrect");
        }

        if (newPassword == null || newPassword.length() < 6) {
            throw new InvalidUserException("Password too short");
        }

        this.password = newPassword;
        this.updatedAt = LocalDateTime.now();
    }

    public void markLogin() {
        this.lastLogin = LocalDateTime.now();
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
        this.status = UserStatus.INACTIVE;
    }
}