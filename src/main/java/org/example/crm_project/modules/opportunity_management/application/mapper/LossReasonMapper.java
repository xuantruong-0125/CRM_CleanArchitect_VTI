package org.example.crm_project.modules.opportunity_management.application.mapper;

import org.example.crm_project.modules.opportunity_management.application.dto.LossReasonRequest;
import org.example.crm_project.modules.opportunity_management.application.dto.LossReasonResponse;
import org.example.crm_project.modules.opportunity_management.domain.entity.LossReason;
import org.springframework.stereotype.Component;

/**
 * Mapper LossReason: Domain ↔ DTO.
 */
@Component
public class LossReasonMapper {

    public LossReason toDomain(LossReasonRequest request) {
        return LossReason.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .isActive(request.getIsActive())
                .build();
    }

    public LossReasonResponse toResponse(LossReason lossReason) {
        return LossReasonResponse.builder()
                .id(lossReason.getId())
                .name(lossReason.getName())
                .description(lossReason.getDescription())
                .isActive(lossReason.getIsActive())
                .build();
    }
}
