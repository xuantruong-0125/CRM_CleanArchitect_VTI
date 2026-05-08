package org.example.crm_project.modules.task_managerment.application.dto.response;

import java.time.LocalDateTime;

import org.example.crm_project.modules.task_managerment.domain.constant.TaskPriority;
import org.example.crm_project.modules.task_managerment.domain.constant.TaskStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskResponse {
    private Long id;
    private String subject;

    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private Integer progressPercent;

    private String relatedToType;
    private Long relatedToId;

    // private Long assignedTo;
    // private String assigneeName;
    // private Long assignedBy;
    private Long contactId;
    // private String contactName;

    private SimpleUserResponse assignee;

    // private SimpleContactResponse contact;
    @Data
    @Builder
    public static class SimpleUserResponse {
        private Long id;
        private String name;
    }

    // @Data
    // @Builder
    // public static class SimpleContactResponse {
    // private Long id;
    // private String name;
    // }

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dueDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime completedAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

}
