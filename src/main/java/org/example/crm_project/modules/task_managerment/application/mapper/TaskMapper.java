package org.example.crm_project.modules.task_managerment.application.mapper;

import org.example.crm_project.modules.task_managerment.application.dto.response.TaskResponse;
import org.example.crm_project.modules.task_managerment.domain.entity.Task;

public class TaskMapper {
    public static TaskResponse toResponse(Task task, String assigneeName, String contactName, String relatedToName) {
        if (task == null)
            return null;
        return TaskResponse.builder()
                .id(task.getId())
                .subject(task.getSubject())
                .description(task.getDescription())
                .startDate(task.getStartDate())
                .dueDate(task.getDueDate())
                .status(task.getStatus())
                .priority(task.getPriority())
                .progressPercent(task.getProgressPercent())

                .relatedToType(task.getRelatedToType())
                .relatedToId(task.getRelatedToId())
                .relatedToName(relatedToName)

                .completedAt(task.getCompletedAt())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())


                .assignee(task.getAssignedTo() != null ? TaskResponse.SimpleUserResponse.builder()
                        .id(task.getAssignedTo())
                        .name(assigneeName)
                        .build()
                        : null)

                .contactId(task.getContactId())
                .contactName(contactName)

                .build();
    }

}
