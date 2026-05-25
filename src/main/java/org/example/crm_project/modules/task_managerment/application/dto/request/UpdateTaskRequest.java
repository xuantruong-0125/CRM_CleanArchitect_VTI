package org.example.crm_project.modules.task_managerment.application.dto.request;

import java.time.LocalDateTime;

import org.example.crm_project.modules.task_managerment.domain.constant.TaskPriority;
import org.example.crm_project.modules.task_managerment.domain.constant.TaskStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UpdateTaskRequest {
    private String subject;
    private String description;
    private TaskStatus status;
    private Integer progressPercent;
    private TaskPriority priority;
    private LocalDateTime startDate;
    private LocalDateTime dueDate;

    private Long assigneeId;
    private Long contactId;

}
