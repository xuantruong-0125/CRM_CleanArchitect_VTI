package org.example.crm_project.modules.system_managerment.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UpsertRoleMenuPermissionRequest {

    @NotNull(message = "roleId must not be null")
    private Long roleId;

    @NotEmpty(message = "permissions must not be empty")
    @Valid
    private List<PermissionItem> permissions;
}