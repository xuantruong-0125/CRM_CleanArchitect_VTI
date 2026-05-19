package org.example.crm_project.modules.task_managerment.infrastructure.persistence.mapper;

import org.example.crm_project.modules.task_managerment.domain.entity.Task;
import org.example.crm_project.modules.task_managerment.infrastructure.persistence.entity.TaskJpaEntity;

public class TaskJpaMapper {

    // 1. Dịch từ Entity (DB) -> Domain (Java)
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
                jpa.getOrganizationId(),
                jpa.getContactId(),
                jpa.getCreatedAt(),
                jpa.getUpdatedAt());
    }

    // Dịch từ Domain (Java) -> Entity (DB)
    public static TaskJpaEntity toJpa(Task domain) {
        if (domain == null) {
            return null;
        }
        TaskJpaEntity jpa = new TaskJpaEntity();
        jpa.setId(domain.getId());
        jpa.setSubject(domain.getSubject());
        jpa.setDescription(domain.getDescription());
        jpa.setStartDate(domain.getStartDate());
        jpa.setDueDate(domain.getDueDate());
        jpa.setCompletedAt(domain.getCompletedAt());
        jpa.setStatus(domain.getStatus());
        jpa.setPriority(domain.getPriority());
        jpa.setProgressPercent(domain.getProgressPercent());
        jpa.setRelatedToType(domain.getRelatedToType());
        jpa.setRelatedToId(domain.getRelatedToId());
        jpa.setAssignedTo(domain.getAssignedTo());
        jpa.setAssignedBy(domain.getAssignedBy());
        jpa.setOrganizationId(domain.getOrganizationId());
        jpa.setContactId(domain.getContactId());
        jpa.setCreatedAt(domain.getCreatedAt());
        jpa.setUpdatedAt(domain.getUpdatedAt());
        
        return jpa;
    }

}
