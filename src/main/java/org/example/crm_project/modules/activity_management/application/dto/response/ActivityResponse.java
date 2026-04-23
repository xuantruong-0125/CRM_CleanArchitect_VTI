package org.example.crm_project.modules.activity_management.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.example.crm_project.modules.activity_management.domain.constant.ActivityStatus;
import org.example.crm_project.modules.activity_management.domain.constant.ActivityType;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor  
@AllArgsConstructor
public class ActivityResponse implements Serializable{
    private Long id;
    private ActivityType activityType;
    private String subject;
    private String description;
    private String relatedToType;
    private Long relatedToId;
    private UserSummaryResponse performedBy;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime completedAt;
    private String outcome;
    private ActivityStatus status;
    private boolean isImportant;
}