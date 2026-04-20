package org.example.crm_project.modules.leads.infrastructure.persistence.mapper;

import org.example.crm_project.modules.leads.domain.constant.LeadActivityType;
import org.example.crm_project.modules.leads.domain.entity.LeadActivity;
import org.example.crm_project.modules.leads.infrastructure.persistence.entity.LeadActivityEntity;

public class LeadActivityJpaMapper {

    public static LeadActivityEntity toEntity(LeadActivity leadActivity) {
        LeadActivityEntity entity = new LeadActivityEntity();
        entity.setId(leadActivity.getId());
        entity.setActivityType(leadActivity.getActivityType() == null ? null : leadActivity.getActivityType().getCode());
        entity.setSubject(leadActivity.getSubject());
        entity.setDescription(leadActivity.getDescription());
        entity.setStartDate(leadActivity.getStartDate());
        entity.setEndDate(leadActivity.getEndDate());
        entity.setCompletedAt(leadActivity.getCompletedAt());
        entity.setOutcome(leadActivity.getOutcome());
        entity.setRelatedToType("LEAD");
        entity.setRelatedToId(leadActivity.getLeadId());
        entity.setPerformedBy(leadActivity.getPerformedBy());
        entity.setCreatedBy(leadActivity.getCreatedBy());
        entity.setUpdatedBy(leadActivity.getUpdatedBy());
        entity.setCreatedAt(leadActivity.getCreatedAt());
        entity.setUpdatedAt(leadActivity.getUpdatedAt());
        entity.setStatus(leadActivity.getStatus());
        entity.setIsImportant(leadActivity.getIsImportant());
        return entity;
    }

    public static LeadActivity toDomain(LeadActivityEntity entity) {
        return LeadActivity.builder()
                .id(entity.getId())
                .leadId(entity.getRelatedToId())
                .activityType(LeadActivityType.fromCode(entity.getActivityType()))
                .subject(entity.getSubject())
                .description(entity.getDescription())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .completedAt(entity.getCompletedAt())
                .outcome(entity.getOutcome())
                .performedBy(entity.getPerformedBy())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .status(entity.getStatus())
                .isImportant(entity.getIsImportant())
                .build();
    }
}