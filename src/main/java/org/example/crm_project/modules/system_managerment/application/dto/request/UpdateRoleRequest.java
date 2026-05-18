package org.example.crm_project.modules.system_managerment.application.dto.request;

import lombok.Data;

@Data
public class UpdateRoleRequest {
    private String name;
    private String description;
    private String scope;
}