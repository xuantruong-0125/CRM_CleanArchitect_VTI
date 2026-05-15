package org.example.crm_project.modules.opportunity_management.infrastructure.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

/**
 * JPA Entity – StageChecklist.
 */
@Data
@Entity
@Table(name = "stage_checklists")
public class StageChecklistJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "task_name", nullable = false, length = 200)
    private String taskName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_mandatory")
    private Boolean isMandatory;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stage_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private PipelineStageJpaEntity stage;
}
