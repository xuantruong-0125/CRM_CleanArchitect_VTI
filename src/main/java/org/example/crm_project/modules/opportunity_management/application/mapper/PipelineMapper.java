package org.example.crm_project.modules.opportunity_management.application.mapper;

import org.example.crm_project.modules.opportunity_management.application.dto.PipelineRequest;
import org.example.crm_project.modules.opportunity_management.application.dto.PipelineResponse;
import org.example.crm_project.modules.opportunity_management.domain.entity.Pipeline;
import org.springframework.stereotype.Component;

/**
 * Mapper Pipeline: Domain ↔ DTO.
 */
@Component
public class PipelineMapper {

    public Pipeline toDomain(PipelineRequest request) {
        return Pipeline.builder()
                .id(request.getId())
                .name(request.getName())
                .build();
    }

    public PipelineResponse toResponse(Pipeline pipeline) {
        return PipelineResponse.builder()
                .id(pipeline.getId())
                .name(pipeline.getName())
                .build();
    }
}
