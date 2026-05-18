package org.example.crm_project.modules.system_managerment.application.dto.request;

import lombok.Data;

@Data
public class UpdateUserRequest {

    private String email;
    private String fullName;

    private Long roleId;
    private Long organizationId;

    private String status; // ACTIVE / INACTIVE / LOCKED
}