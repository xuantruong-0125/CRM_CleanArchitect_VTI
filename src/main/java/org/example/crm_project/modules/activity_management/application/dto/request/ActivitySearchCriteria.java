package org.example.crm_project.modules.activity_management.application.dto.request;

import java.time.LocalDateTime;

public record ActivitySearchCriteria(
        String search,
        Integer status,
        String activityType,
        Long performedBy,
        Long relatedToId,
        String relatedToType,
        LocalDateTime fromDate,
        LocalDateTime toDate,
        Boolean important) {

}
