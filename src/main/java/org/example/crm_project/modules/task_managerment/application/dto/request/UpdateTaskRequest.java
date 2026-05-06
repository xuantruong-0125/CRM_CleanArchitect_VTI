package org.example.crm_project.modules.task_managerment.application.dto.request;

import java.time.LocalDateTime;

import org.example.crm_project.modules.task_managerment.domain.constant.TaskPriority;
import org.example.crm_project.modules.task_managerment.domain.constant.TaskStatus;
import lombok.Data;

@Data
public class UpdateTaskRequest {
    private String subject;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime dueDate;
    private TaskStatus status;
    private TaskPriority priority;
    private Integer progressPercent;

}
