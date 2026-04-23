package org.example.crm_project.modules.system_managerment.presentation.dto.request;

import lombok.Data;

@Data
public class CreateUserRequestDto {

    private String username;
    private String password;
    private String email;
    private String fullName;

    private Long roleId;
    private Long organizationId;
}