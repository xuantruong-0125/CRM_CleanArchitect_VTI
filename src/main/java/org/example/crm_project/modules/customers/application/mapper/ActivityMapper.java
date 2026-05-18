package org.example.crm_project.modules.customers.application.mapper;

import org.example.crm_project.modules.customers.application.dto.request.CreateActivityDTO;
import org.example.crm_project.modules.customers.application.dto.response.ActivityResponseDTO;
import org.example.crm_project.modules.customers.domain.entity.Activity;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {

    public Activity toEntity(CreateActivityDTO dto) {
        if (dto == null) {
            return null;
        }
        Activity activity = new Activity();
        activity.setActivityType(dto.getActivityType());
        activity.setSubject(dto.getSubject());
        activity.setDescription(dto.getDescription());
        activity.setStartDate(dto.getStartDate());
        activity.setEndDate(dto.getEndDate());
        activity.setCompletedAt(dto.getCompletedAt());
        activity.setOutcome(dto.getOutcome());
        activity.setRelatedToType(dto.getRelatedToType());
        activity.setRelatedToId(dto.getRelatedToId());
        activity.setPerformedBy(dto.getPerformedBy());
        activity.setIsImportant(dto.getIsImportant());
        activity.setStatus(dto.getStatus());
        return activity;
    }

    public ActivityResponseDTO toResponseDTO(Activity entity) {
        if (entity == null) {
            return null;
        }
        ActivityResponseDTO dto = new ActivityResponseDTO();
        dto.setId(entity.getId());
        dto.setActivityType(entity.getActivityType());
        dto.setSubject(entity.getSubject());
        dto.setDescription(entity.getDescription());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setCompletedAt(entity.getCompletedAt());
        dto.setOutcome(entity.getOutcome());
        dto.setRelatedToType(entity.getRelatedToType());
        dto.setRelatedToId(entity.getRelatedToId());
        dto.setPerformedBy(entity.getPerformedBy());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setStatus(entity.getStatus());
        dto.setIsImportant(entity.getIsImportant());
        return dto;
    }

    public void updateEntityFromDTO(Activity activity, CreateActivityDTO dto) {
        if (dto == null || activity == null) {
            return;
        }
        if (dto.getActivityType() != null) activity.setActivityType(dto.getActivityType());
        if (dto.getSubject() != null) activity.setSubject(dto.getSubject());
        if (dto.getDescription() != null) activity.setDescription(dto.getDescription());
        if (dto.getStartDate() != null) activity.setStartDate(dto.getStartDate());
        if (dto.getEndDate() != null) activity.setEndDate(dto.getEndDate());
        if (dto.getCompletedAt() != null) activity.setCompletedAt(dto.getCompletedAt());
        if (dto.getOutcome() != null) activity.setOutcome(dto.getOutcome());
        if (dto.getRelatedToType() != null) activity.setRelatedToType(dto.getRelatedToType());
        if (dto.getRelatedToId() != null) activity.setRelatedToId(dto.getRelatedToId());
        if (dto.getPerformedBy() != null) activity.setPerformedBy(dto.getPerformedBy());
        if (dto.getIsImportant() != null) activity.setIsImportant(dto.getIsImportant());
        if (dto.getStatus() != null) activity.setStatus(dto.getStatus());
    }
}
