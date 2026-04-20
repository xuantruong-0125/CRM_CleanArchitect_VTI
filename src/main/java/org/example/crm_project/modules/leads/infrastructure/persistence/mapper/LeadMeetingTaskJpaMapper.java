package org.example.crm_project.modules.leads.infrastructure.persistence.mapper;

import org.example.crm_project.modules.leads.domain.entity.LeadMeetingTask;
import org.example.crm_project.modules.leads.infrastructure.persistence.entity.LeadMeetingTaskEntity;

public class LeadMeetingTaskJpaMapper {

    public static LeadMeetingTaskEntity toEntity(LeadMeetingTask meetingTask) {
        LeadMeetingTaskEntity entity = new LeadMeetingTaskEntity();
        entity.setId(meetingTask.getId());
        entity.setSubject(meetingTask.getSubject());
        entity.setDescription(meetingTask.getDescription());
        entity.setStartDate(meetingTask.getStartDate());
        entity.setDueDate(meetingTask.getDueDate());
        entity.setCompletedAt(meetingTask.getCompletedAt());
        entity.setStatus(meetingTask.getStatus());
        entity.setPriority(meetingTask.getPriority());
        entity.setProgressPercent(meetingTask.getProgressPercent());
        entity.setRelatedToType("LEAD");
        entity.setRelatedToId(meetingTask.getLeadId());
        entity.setAssignedTo(meetingTask.getAssignedTo());
        entity.setAssignedBy(meetingTask.getAssignedBy());
        entity.setCreatedBy(meetingTask.getCreatedBy());
        entity.setUpdatedBy(meetingTask.getUpdatedBy());
        entity.setCreatedAt(meetingTask.getCreatedAt());
        entity.setUpdatedAt(meetingTask.getUpdatedAt());
        entity.setContactId(meetingTask.getContactId());
        return entity;
    }

    public static LeadMeetingTask toDomain(LeadMeetingTaskEntity entity) {
        return LeadMeetingTask.builder()
                .id(entity.getId())
                .leadId(entity.getRelatedToId())
                .subject(entity.getSubject())
                .description(entity.getDescription())
                .startDate(entity.getStartDate())
                .dueDate(entity.getDueDate())
                .completedAt(entity.getCompletedAt())
                .status(entity.getStatus())
                .priority(entity.getPriority())
                .progressPercent(entity.getProgressPercent())
                .assignedTo(entity.getAssignedTo())
                .assignedBy(entity.getAssignedBy())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .contactId(entity.getContactId())
                .build();
    }
}