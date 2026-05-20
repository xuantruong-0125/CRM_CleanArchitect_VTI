package org.example.crm_project.modules.opportunity_management.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * JPA Entity – LossReason.
 */
@Data
@Entity
@Table(name = "loss_reasons")
public class LossReasonJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_active")
    private Boolean isActive;
}
