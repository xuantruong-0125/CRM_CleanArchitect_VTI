package org.example.crm_project.modules.auth.domain.entity;

import java.util.Set;

public class AuthUser {

    private Long id;
    private String username;
    private String fullname;
    private String password;
    private boolean active;
    private Set<String> roles;
    private Set<String> permissions;


    public AuthUser(Long id, String username, String fullname, String password,
                    boolean active,
                    Set<String> roles, Set<String> permissions) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.active = active;
        this.roles = roles;
        this.permissions = permissions;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public boolean isActive() { return active; }
    public Set<String> getRoles() { return roles; }
    public Set<String> getPermissions() { return permissions; }
    public String getFullname() {
        return fullname;
    }
}