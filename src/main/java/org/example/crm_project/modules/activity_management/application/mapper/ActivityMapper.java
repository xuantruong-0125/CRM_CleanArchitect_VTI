package org.example.crm_project.modules.activity_management.application.mapper;

import org.example.crm_project.modules.activity_management.application.dto.request.CreateActivityRequest;
import org.example.crm_project.modules.activity_management.application.dto.response.ActivityResponse;
import org.example.crm_project.modules.activity_management.application.dto.response.UserSummaryResponse;
import org.example.crm_project.modules.activity_management.domain.constant.ActivityStatus;
import org.example.crm_project.modules.activity_management.domain.entity.Activity;

public class ActivityMapper {

    public static ActivityResponse toResponse(Activity entity, String userName,String relatedToName) {
        if (entity == null)
            return null;

        return ActivityResponse.builder()
                .id(entity.getId())
                .activityType(entity.getActivityType())
                .subject(entity.getSubject())
                .description(entity.getDescription())
                .relatedToType(entity.getRelatedToType())
                .relatedToId(entity.getRelatedToId())
                .relatedToName(relatedToName)
                // Đóng gói thành object {id, name}
                .performedBy(new UserSummaryResponse(entity.getPerformedBy(), userName))
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .completedAt(entity.getCompletedAt())
                .outcome(entity.getOutcome())
                .status(entity.getStatus())
                .isImportant(entity.isImportant())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static Activity toEntity(CreateActivityRequest request) {
        if (request == null)
            return null;
        return Activity.builder()
                .activityType(request.getActivityType())
                .subject(request.getSubject())
                .description(request.getDescription())
                .relatedToType(request.getRelatedToType())
                .relatedToId(request.getRelatedToId())
                .performedBy(request.getPerformedBy())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .status(request.getStatus() != null ? request.getStatus() : ActivityStatus.PLANNED)
                .isImportant(request.getImportant() != null ? request.getImportant() : false)
                .build();
    }
}