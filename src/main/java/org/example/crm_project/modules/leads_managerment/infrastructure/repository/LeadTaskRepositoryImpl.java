package org.example.crm_project.modules.leads_managerment.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.leads_managerment.domain.entity.LeadTask;
import org.example.crm_project.modules.leads_managerment.domain.repository.LeadTaskRepository;
import org.example.crm_project.modules.leads_managerment.infrastructure.persistence.repository.JpaLeadTaskRepository;
import org.example.crm_project.modules.task_managerment.infrastructure.persistence.entity.TaskJpaEntity;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LeadTaskRepositoryImpl implements LeadTaskRepository {
    private final JpaLeadTaskRepository jpaLeadTaskRepository;
    @Override
    public List<LeadTask> findByLeadId(Long leadId) {
        List<TaskJpaEntity> jpaEntities = jpaLeadTaskRepository.findByLeadId(leadId);
        return jpaEntities.stream()
                .map(this::toDomain)
                .toList();
    }
    private LeadTask toDomain(TaskJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        return LeadTask.builder()
                .id(entity.getId())
                .subject(entity.getSubject())
                .description(entity.getDescription())
                .startDate(entity.getStartDate())
                .dueDate(entity.getDueDate())
                .completedAt(entity.getCompletedAt())
                .status(entity.getStatus() != null ? entity.getStatus().name() : null)
                .priority(entity.getPriority() != null ? entity.getPriority().name() : null)
                .progressPercent(entity.getProgressPercent())
                .assignedTo(entity.getAssignedTo())
                .contactId(entity.getContactId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}