package org.example.crm_project.modules.system_managerment.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PermissionItem {

    @NotNull(message = "menuId must not be null")
    private Long menuId;

    private boolean canView;
    private boolean canCreate;
    private boolean canUpdate;
    private boolean canDelete;
}