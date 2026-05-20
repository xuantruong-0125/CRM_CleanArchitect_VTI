package org.example.crm_project.modules.opportunity_management.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * JPA Entity – Customer (tham chiếu từ module ngoài).
 */
@Data
@Entity
@Table(name = "customers")
public class CustomerJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(name = "short_name")
    private String shortName;

    private String phone;
    private String email;
}
