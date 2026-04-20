package org.example.crm_project.modules.leads.application.mapper;

import org.example.crm_project.modules.leads.application.dto.request.CreateLeadMeetingRequest;
import org.example.crm_project.modules.leads.application.dto.response.LeadMeetingTaskResponse;
import org.example.crm_project.modules.leads.application.dto.request.UpdateLeadMeetingRequest;
import org.example.crm_project.modules.leads.domain.entity.LeadMeetingTask;

public class LeadMeetingTaskMapper {

    public static LeadMeetingTask toDomain(Long leadId, CreateLeadMeetingRequest request) {
        return LeadMeetingTask.builder()
                .leadId(leadId)
                .subject(trim(request.getSubject()))
                .description(trim(request.getDescription()))
                .startDate(request.getStartDate())
                .dueDate(request.getDueDate())
                .completedAt(request.getCompletedAt())
                .status(trim(request.getStatus()))
                .priority(trim(request.getPriority()))
                .progressPercent(request.getProgressPercent())
                .assignedTo(request.getAssignedTo())
                .assignedBy(request.getAssignedBy())
                .createdBy(request.getCreatedBy())
                .contactId(request.getContactId())
                .build();
    }

    public static LeadMeetingTaskResponse toResponse(LeadMeetingTask meetingTask) {
        return LeadMeetingTaskResponse.builder()
                .id(meetingTask.getId())
                .leadId(meetingTask.getLeadId())
                .taskType("MEETING")
                .subject(meetingTask.getSubject())
                .description(meetingTask.getDescription())
                .startDate(meetingTask.getStartDate())
                .dueDate(meetingTask.getDueDate())
                .completedAt(meetingTask.getCompletedAt())
                .status(meetingTask.getStatus())
                .priority(meetingTask.getPriority())
                .progressPercent(meetingTask.getProgressPercent())
                .assignedTo(meetingTask.getAssignedTo())
                .assignedBy(meetingTask.getAssignedBy())
                .createdBy(meetingTask.getCreatedBy())
                .updatedBy(meetingTask.getUpdatedBy())
                .contactId(meetingTask.getContactId())
                .createdAt(meetingTask.getCreatedAt())
                .updatedAt(meetingTask.getUpdatedAt())
                .build();
    }

    public static LeadMeetingTask merge(LeadMeetingTask existing, UpdateLeadMeetingRequest request) {
        LeadMeetingTask updated = LeadMeetingTask.builder()
                .id(existing.getId())
                .leadId(existing.getLeadId())
                .subject(existing.getSubject())
                .description(existing.getDescription())
                .startDate(existing.getStartDate())
                .dueDate(existing.getDueDate())
                .completedAt(existing.getCompletedAt())
                .status(existing.getStatus())
                .priority(existing.getPriority())
                .progressPercent(existing.getProgressPercent())
                .assignedTo(existing.getAssignedTo())
                .assignedBy(existing.getAssignedBy())
                .createdBy(existing.getCreatedBy())
                .updatedBy(existing.getUpdatedBy())
                .contactId(existing.getContactId())
                .createdAt(existing.getCreatedAt())
                .updatedAt(existing.getUpdatedAt())
                .build();

        if (request.getSubject() != null) {
            updated.setSubject(trim(request.getSubject()));
        }
        if (request.getDescription() != null) {
            updated.setDescription(trim(request.getDescription()));
        }
        if (request.getStartDate() != null) {
            updated.setStartDate(request.getStartDate());
        }
        if (request.getDueDate() != null) {
            updated.setDueDate(request.getDueDate());
        }
        if (request.getCompletedAt() != null) {
            updated.setCompletedAt(request.getCompletedAt());
        }
        if (request.getStatus() != null) {
            updated.setStatus(trim(request.getStatus()));
        }
        if (request.getPriority() != null) {
            updated.setPriority(trim(request.getPriority()));
        }
        if (request.getProgressPercent() != null) {
            updated.setProgressPercent(request.getProgressPercent());
        }
        if (request.getAssignedTo() != null) {
            updated.setAssignedTo(request.getAssignedTo());
        }
        if (request.getAssignedBy() != null) {
            updated.setAssignedBy(request.getAssignedBy());
        }
        if (request.getUpdatedBy() != null) {
            updated.setUpdatedBy(request.getUpdatedBy());
        }
        if (request.getContactId() != null) {
            updated.setContactId(request.getContactId());
        }

        return updated;
    }

    private static String trim(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}