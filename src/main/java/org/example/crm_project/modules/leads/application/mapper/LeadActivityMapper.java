package org.example.crm_project.modules.leads.application.mapper;

import org.example.crm_project.modules.leads.application.dto.request.CreateLeadActivityRequest;
import org.example.crm_project.modules.leads.application.dto.response.LeadActivityResponse;
import org.example.crm_project.modules.leads.application.dto.response.LeadActivityStatisticsResponse;
import org.example.crm_project.modules.leads.domain.constant.LeadActivityType;
import org.example.crm_project.modules.leads.domain.entity.LeadActivity;
import org.example.crm_project.modules.leads.domain.entity.LeadActivityStatistics;

public class LeadActivityMapper {

    public static LeadActivity toDomain(Long leadId, CreateLeadActivityRequest request) {
        return LeadActivity.builder()
                .leadId(leadId)
                .activityType(LeadActivityType.fromName(request.getActivityType()))
                .subject(trim(request.getSubject()))
                .description(trim(request.getDescription()))
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .completedAt(request.getCompletedAt())
                .outcome(trim(request.getOutcome()))
                .performedBy(request.getPerformedBy())
                .createdBy(request.getCreatedBy())
                .status(request.getStatus())
                .isImportant(request.getIsImportant())
                .build();
    }

    public static LeadActivityResponse toResponse(LeadActivity leadActivity) {
        return LeadActivityResponse.builder()
                .id(leadActivity.getId())
                .leadId(leadActivity.getLeadId())
                .activityType(leadActivity.getActivityType() == null ? null : leadActivity.getActivityType().name())
                .subject(leadActivity.getSubject())
                .description(leadActivity.getDescription())
                .startDate(leadActivity.getStartDate())
                .endDate(leadActivity.getEndDate())
                .completedAt(leadActivity.getCompletedAt())
                .outcome(leadActivity.getOutcome())
                .performedBy(leadActivity.getPerformedBy())
                .createdBy(leadActivity.getCreatedBy())
                .updatedBy(leadActivity.getUpdatedBy())
                .createdAt(leadActivity.getCreatedAt())
                .updatedAt(leadActivity.getUpdatedAt())
                .status(leadActivity.getStatus())
                .isImportant(leadActivity.getIsImportant())
                .build();
    }

    public static LeadActivityStatisticsResponse toStatisticsResponse(LeadActivityStatistics statistics) {
        return LeadActivityStatisticsResponse.builder()
                .callCount(statistics.getCallCount())
                .meetingCount(statistics.getMeetingCount())
                .emailCount(statistics.getEmailCount())
                .totalCount(statistics.getTotalCount())
                .build();
    }

    private static String trim(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}