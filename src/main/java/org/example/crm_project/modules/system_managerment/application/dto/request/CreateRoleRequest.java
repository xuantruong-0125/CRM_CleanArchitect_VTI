package org.example.crm_project.modules.system_managerment.application.dto.request;

import lombok.Data;

@Data
public class CreateRoleRequest {
    private String name;
    private String description;
    private String scope; // 👈 nhận từ client dạng String
}