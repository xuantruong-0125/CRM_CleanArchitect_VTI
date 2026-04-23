package org.example.crm_project.modules.activity_management.application.dto.request;

import lombok.Data;
import org.example.crm_project.modules.activity_management.domain.constant.ActivityType;
import java.time.LocalDateTime;

@Data
public class CreateActivityRequest {
    private ActivityType activityType;
    private String subject;
    private String description;
    private String relatedToType;
    private Long relatedToId;
    private Long performedBy;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}