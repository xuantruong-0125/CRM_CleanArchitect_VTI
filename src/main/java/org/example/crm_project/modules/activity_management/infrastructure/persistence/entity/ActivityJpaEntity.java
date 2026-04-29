package org.example.crm_project.modules.activity_management.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "activities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Cột 2: NOT NULL
    @Column(name = "activity_type", columnDefinition = "TINYINT")
    private Integer activityType;

    // Cột 3: NOT NULL, varchar(255)
    @Column(name = "subject", nullable = false, length = 255)
    private String subject;

    // Cột 4: TEXT
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // Cột 5, 6, 7: datetime
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    // Cột 8: varchar(100)
    @Column(name = "outcome", length = 100)
    private String outcome;

    // Cột 9: varchar(50)
    @Column(name = "related_to_type", length = 50)
    private String relatedToType;

    // Cột 10: NOT NULL
    @Column(name = "related_to_id", nullable = false)
    private Long relatedToId;

    // Cột 11: NOT NULL
    @Column(name = "performed_by", nullable = false)
    private Long performedBy;

    // Cột 12, 13: Audit logs
    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    // Cột 14: timestamp (updatable = false để Hibernate không ghi đè khi update)
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Cột 15: timestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "status", columnDefinition = "TINYINT")
    private Integer status;

    @Column(name = "is_important", columnDefinition = "TINYINT(1)")
    private Boolean isImportant;
    // Cột 18: Soft delete
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}