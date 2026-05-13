package org.example.crm_project.modules.opportunity_management.infrastructure.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

/**
 * JPA Entity – PipelineStage.
 */
@Data
@Entity
@Table(name = "pipeline_stages")
public class PipelineStageJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "stage_name", nullable = false, length = 100)
    private String stageName;

    private Float probability;

    @Column(name = "max_days_allowed")
    private Integer maxDaysAllowed;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pipeline_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private PipelineJpaEntity pipeline;
}
