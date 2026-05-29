package org.example.crm_project.modules.task_managerment.application.dto.request;

import java.time.LocalDateTime;

import org.example.crm_project.modules.task_managerment.domain.constant.TaskPriority;
import lombok.Data;

@Data
public class CreateTaskRequest {
    private String subject;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime dueDate; 
    private TaskPriority priority;
    
    // Liên kết dữ liệu
    private String relatedToType;
    private Long relatedToId;
    private Long assignedTo;
    private Long contactId;

}
