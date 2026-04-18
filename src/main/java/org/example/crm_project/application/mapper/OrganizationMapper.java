package org.example.crm_project.application.mapper;

import org.example.crm_project.application.dto.request.CreateOrganizationRequest;
import org.example.crm_project.application.dto.response.OrganizationResponse;
import org.example.crm_project.domain.constant.OrganizationType;
import org.example.crm_project.domain.entity.Organization;

public class OrganizationMapper {

    // CREATE
    public static Organization toEntity(CreateOrganizationRequest req) {
        return new Organization(
                req.getName(),
                req.getParentId(),
                OrganizationType.valueOf(req.getType())
        );
    }

    // RESPONSE
    public static OrganizationResponse toResponse(Organization org) {
        return OrganizationResponse.builder()
                .id(org.getId())
                .name(org.getName())
                .parentId(org.getParentId())
                .type(org.getType().name())
                .build();
    }
}