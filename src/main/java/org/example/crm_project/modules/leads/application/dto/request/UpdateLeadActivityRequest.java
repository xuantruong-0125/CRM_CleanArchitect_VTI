package org.example.crm_project.modules.leads.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLeadActivityRequest {

    private String activityType;
    private String subject;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime completedAt;
    private String outcome;
    private Long performedBy;
    private Long updatedBy;
    private Integer status;
    private Boolean isImportant;
}