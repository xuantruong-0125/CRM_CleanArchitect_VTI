package org.example.crm_project.modules.activity_management.application.dto.request;

import lombok.Data;
import org.example.crm_project.modules.activity_management.domain.constant.ActivityStatus;
import org.example.crm_project.modules.activity_management.domain.constant.ActivityType;

@Data
public class UpdateActivityRequest {
    private String subject;
    private String description;
    private ActivityStatus status; 
    private ActivityType activityType;
}