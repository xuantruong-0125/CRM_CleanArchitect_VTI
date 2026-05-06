package org.example.crm_project.modules.task_managerment.infrastructure.persistence.mapper;

import org.example.crm_project.modules.task_managerment.domain.entity.Task;
import org.example.crm_project.modules.task_managerment.infrastructure.persistence.entity.TaskJpaEntity;

public class TaskJpaMapper {
    public static Task toDomain(TaskJpaEntity jpa) {
        if (jpa == null)
            return null;
        return new Task(
                jpa.getId(),
                jpa.getSubject(),
                jpa.getDescription(),
                jpa.getStartDate(),
                jpa.getDueDate(),
                jpa.getCompletedAt(),
                jpa.getStatus(),
                jpa.getPriority(),
                jpa.getProgressPercent(),
                jpa.getRelatedToType(),
                jpa.getRelatedToId(),
                jpa.getAssignedTo(),
                jpa.getAssignedBy(),
                jpa.getContactId(),
                jpa.getCreatedAt(),
                jpa.getUpdatedAt());
    }

}
