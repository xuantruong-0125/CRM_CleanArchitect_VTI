package org.example.crm_project.modules.opportunity_management.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * JPA Entity – User (tham chiếu từ module ngoài).
 */
@Data
@Entity
@Table(name = "users")
public class UserJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "full_name")
    private String fullName;

    private String email;
    private String status;
}
