package org.example.crm_project.modules.system_managerment.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    
    private String password;
    
    private String email;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "organization_id")
    private Integer organizationId;

    private String status;
}
