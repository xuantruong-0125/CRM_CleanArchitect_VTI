package org.example.crm_project.application.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrganizationResponse {
    private Long id;
    private String name;
    private Long parentId;
    private String type;
}