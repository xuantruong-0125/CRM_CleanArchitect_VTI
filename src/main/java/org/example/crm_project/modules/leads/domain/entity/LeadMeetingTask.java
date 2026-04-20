package org.example.crm_project.modules.leads.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeadMeetingTask {

    private Long id;
    private Long leadId;
    private String subject;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime dueDate;
    private LocalDateTime completedAt;
    private String status;
    private String priority;
    private Integer progressPercent;
    private Long assignedTo;
    private Long assignedBy;
    private Long createdBy;
    private Long updatedBy;
    private Long contactId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}