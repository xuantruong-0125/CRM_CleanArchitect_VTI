package org.example.crm_project.modules.opportunity_management.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * JPA Entity – Pipeline.
 */
@Data
@Entity
@Table(name = "pipelines")
public class PipelineJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    private String name;
}
