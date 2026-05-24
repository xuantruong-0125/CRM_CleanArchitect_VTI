package org.example.crm_project.modules.leads_managerment.application.mapper;

import org.example.crm_project.modules.leads_managerment.application.dto.response.LeadReferenceOptionResponse;
import org.example.crm_project.modules.leads_managerment.domain.entity.LeadReferenceOption;

public class LeadReferenceMapper {

    public static LeadReferenceOptionResponse toResponse(LeadReferenceOption option) {
        return LeadReferenceOptionResponse.builder()
                .id(option.getId())
                .code(option.getCode())
                .name(option.getName())
                .build();
    }
}
