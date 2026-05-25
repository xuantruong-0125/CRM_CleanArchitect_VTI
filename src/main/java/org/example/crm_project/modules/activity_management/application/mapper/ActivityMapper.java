package org.example.crm_project.modules.activity_management.application.mapper;

import org.example.crm_project.modules.activity_management.application.dto.request.CreateActivityRequest;
import org.example.crm_project.modules.activity_management.application.dto.response.ActivityResponse;
import org.example.crm_project.modules.activity_management.application.dto.response.UserSummaryResponse;
import org.example.crm_project.modules.activity_management.domain.constant.ActivityStatus;
import org.example.crm_project.modules.activity_management.domain.entity.Activity;

public class ActivityMapper {

    public static ActivityResponse toResponse(Activity entity, String userName) {
        if (entity == null)
            return null;

        return ActivityResponse.builder()
                .id(entity.getId())
                .activityType(entity.getActivityType())
                .subject(entity.getSubject())
                .description(entity.getDescription())
                .relatedToType(entity.getRelatedToType())
                .relatedToId(entity.getRelatedToId())
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

    // 2. CHIỀU ĐI: Request -> Entity (Dùng cho Thêm Mới)
    public static Activity toEntity(CreateActivityRequest request) {
        if (request == null)
            return null;

        // Lưu ý: Các trường như 'id', 'completedAt', 'outcome'
        // thường không được gửi từ Frontend khi tạo mới nên ta để null (hoặc bỏ qua)
        return Activity.builder()
                .activityType(request.getActivityType())
                .subject(request.getSubject())
                .description(request.getDescription())
                .relatedToType(request.getRelatedToType())
                .relatedToId(request.getRelatedToId())
                .performedBy(request.getPerformedBy())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                // Gán mặc định vì Request không có
                .status(ActivityStatus.PLANNED)
                .isImportant(false)
                .build();
    }
}