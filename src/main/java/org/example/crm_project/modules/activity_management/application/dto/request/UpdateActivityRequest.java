package org.example.crm_project.modules.activity_management.application.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

import org.example.crm_project.modules.activity_management.domain.constant.ActivityStatus;

@Data
public class UpdateActivityRequest {
    private String subject;
    private String description;
    private ActivityStatus status; 
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime completedAt; 
    private String outcome;
    private Boolean important;
    private Long performedBy;
}