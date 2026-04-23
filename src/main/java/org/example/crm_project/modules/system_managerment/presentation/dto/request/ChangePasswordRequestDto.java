package org.example.crm_project.modules.system_managerment.presentation.dto.request;

import lombok.Data;

@Data
public class ChangePasswordRequestDto {

    private String oldPassword;
    private String newPassword;
}