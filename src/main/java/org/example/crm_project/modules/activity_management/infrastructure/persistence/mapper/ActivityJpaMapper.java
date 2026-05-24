package org.example.crm_project.modules.activity_management.infrastructure.persistence.mapper;

import org.example.crm_project.modules.activity_management.domain.constant.ActivityStatus;
import org.example.crm_project.modules.activity_management.domain.constant.ActivityType;
import org.example.crm_project.modules.activity_management.domain.entity.Activity;
import org.example.crm_project.modules.activity_management.infrastructure.persistence.entity.ActivityJpaEntity;

import java.util.Arrays;

public class ActivityJpaMapper {

    public static Activity toDomain(ActivityJpaEntity jpa) {
        if (jpa == null) return null;

        ActivityType type = Arrays.stream(ActivityType.values())
                .filter(t -> t.getValue() == jpa.getActivityType())
                .findFirst()
                .orElse(ActivityType.CALL);

        ActivityStatus status = jpa.getStatus() != null
                ? Arrays.stream(ActivityStatus.values())
                    .filter(s -> s.getValue() == jpa.getStatus())
                    .findFirst()
                    .orElse(ActivityStatus.PLANNED)
                : ActivityStatus.PLANNED;

        return new Activity(
                jpa.getId(),
                type,
                jpa.getSubject(),
                jpa.getDescription(),
                jpa.getStartDate(),
                jpa.getEndDate(),
                jpa.getCompletedAt(),
                jpa.getOutcome(),
                jpa.getRelatedToType(),
                jpa.getRelatedToId(),
                jpa.getPerformedBy(),
                status, // Nhét status đã convert vào đây
                jpa.getIsImportant() != null ? jpa.getIsImportant() : false,
                jpa.getOrganizationId(),
                jpa.getCreatedAt(),
                jpa.getUpdatedAt()

        );
    }

    public static ActivityJpaEntity toJpa(Activity domain) {
        if (domain == null) return null;

        return ActivityJpaEntity.builder()
                .id(domain.getId())
                .activityType(domain.getActivityType() != null ? domain.getActivityType().getValue() : null)
                .subject(domain.getSubject())
                .description(domain.getDescription())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .completedAt(domain.getCompletedAt())
                .outcome(domain.getOutcome())
                .relatedToType(domain.getRelatedToType())
                .relatedToId(domain.getRelatedToId())
                .performedBy(domain.getPerformedBy())
                .status(domain.getStatus() != null ? domain.getStatus().getValue() : null) // Dịch ngược ra số ở đây
                .isImportant(domain.isImportant())
                .build();
    }
}