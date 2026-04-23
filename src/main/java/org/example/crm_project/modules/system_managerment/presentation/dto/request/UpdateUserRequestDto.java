package org.example.crm_project.modules.system_managerment.presentation.dto.request;

import lombok.Data;

@Data
public class UpdateUserRequestDto {

    private String email;
    private String fullName;
    private Long roleId;
    private Long organizationId;
    private String status;
}