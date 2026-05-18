package org.example.crm_project.modules.opportunity_management.application.mapper;

import org.example.crm_project.modules.opportunity_management.application.dto.PipelineStageRequest;
import org.example.crm_project.modules.opportunity_management.application.dto.PipelineStageResponse;
import org.example.crm_project.modules.opportunity_management.domain.entity.PipelineStage;
import org.springframework.stereotype.Component;

/**
 * Mapper PipelineStage: Domain ↔ DTO.
 */
@Component
public class PipelineStageMapper {

    public PipelineStage toDomain(PipelineStageRequest request) {
        return PipelineStage.builder()
                .id(request.getId())
                .stageName(request.getStageName())
                .maxDaysAllowed(request.getMaxDaysAllowed())
                .sortOrder(request.getSortOrder())
                .pipelineId(request.getPipelineId())
                .build();
    }

    public PipelineStageResponse toResponse(PipelineStage stage) {
        return PipelineStageResponse.builder()
                .id(stage.getId())
                .stageName(stage.getStageName())
                .probability(stage.getProbability())
                .maxDaysAllowed(stage.getMaxDaysAllowed())
                .sortOrder(stage.getSortOrder())
                .pipelineId(stage.getPipelineId())
                .pipelineName(stage.getPipelineName())
                .build();
    }
}
