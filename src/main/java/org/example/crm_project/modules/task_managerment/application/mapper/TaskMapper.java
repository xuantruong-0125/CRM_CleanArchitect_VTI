package org.example.crm_project.modules.task_managerment.application.mapper;

import org.example.crm_project.modules.task_managerment.application.dto.response.TaskResponse;
import org.example.crm_project.modules.task_managerment.domain.entity.Task;

public class TaskMapper {
    public static TaskResponse toResponse(Task task, String assigneeName) {
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

                .completedAt(task.getCompletedAt())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())

                // .assignedTo(task.getAssignedTo())
                // .assigneeName(assigneeName)
                // .assignedBy(task.getAssignedBy())
                // Đóng gói Object Assignee (Người thực hiện)
                .assignee(task.getAssignedTo() != null ? TaskResponse.SimpleUserResponse.builder()
                        .id(task.getAssignedTo())
                        .name(assigneeName)
                        .build()
                        : null)

                .contactId(task.getContactId())
                // .contactName(task.getContactId() != null ? "Nguyễn Văn Khách (Fake)" : null)

                // Đóng gói Object Contact (Khách hàng liên hệ - Đang dùng Mock data)
                // .contact(task.getContactId() != null ? TaskResponse.SimpleContactResponse.builder()
                //         .id(task.getContactId())
                //         .name("Nguyễn Văn Khách (Fake)")
                //         .build()
                //         : null)

                .build();
    }

}
