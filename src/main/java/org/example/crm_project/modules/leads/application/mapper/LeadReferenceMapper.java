package org.example.crm_project.modules.leads.application.mapper;

import org.example.crm_project.modules.leads.application.dto.response.LeadReferenceOptionResponse;
import org.example.crm_project.modules.leads.domain.entity.LeadReferenceOption;

public class LeadReferenceMapper {

    public static LeadReferenceOptionResponse toResponse(LeadReferenceOption option) {
        return LeadReferenceOptionResponse.builder()
                .id(option.getId())
                .code(option.getCode())
                .name(option.getName())
                .build();
    }
}