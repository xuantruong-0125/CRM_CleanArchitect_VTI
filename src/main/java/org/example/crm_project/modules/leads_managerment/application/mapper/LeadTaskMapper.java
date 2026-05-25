package org.example.crm_project.modules.leads_managerment.application.mapper;

import org.example.crm_project.modules.leads_managerment.application.dto.response.LeadTaskResponse;
import org.example.crm_project.modules.leads_managerment.domain.entity.LeadTask;

public class LeadTaskMapper {
    public static LeadTaskResponse toResponse(LeadTask task, String assigneeName) {
        if (task == null) {
            return null;
        }
        return LeadTaskResponse.builder()
                .id(task.getId())
                .subject(task.getSubject())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .progressPercent(task.getProgressPercent())
                .startDate(task.getStartDate())
                .dueDate(task.getDueDate())
                .completedAt(task.getCompletedAt())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .assignee(task.getAssignedTo() != null ? LeadTaskResponse.SimpleUserResponse.builder()
                        .id(task.getAssignedTo())
                        .name(assigneeName)
                        .build() : null)
                .build();
    }
}