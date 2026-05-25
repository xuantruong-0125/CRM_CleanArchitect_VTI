package org.example.crm_project.modules.task_managerment.application.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TaskHistoryResponse {
    private Long id;
    private Long taskId;
    private String actorName; // Tên người sửa
    private String fieldName; // Trường bị sửa (status, progressPercent)
    private String oldValue;
    private String newValue;
    private LocalDateTime createdAt;

}
