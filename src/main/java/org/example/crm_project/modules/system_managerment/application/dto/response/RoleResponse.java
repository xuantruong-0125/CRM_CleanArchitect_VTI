package org.example.crm_project.modules.system_managerment.application.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleResponse {
    private Long id;
    private String name;
    private String description;
    private String scope;
}