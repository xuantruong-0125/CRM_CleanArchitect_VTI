package org.example.crm_project.modules.system_managerment.application.dto.request;

import lombok.Data;

@Data
public class ChangePasswordRequest {

    private String oldPassword;
    private String newPassword;
}