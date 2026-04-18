package org.example.crm_project.modules.system_managerment.application.dto.request;

import lombok.Data;

@Data
public class UpsertRoleMenuPermissionRequest {

    private Long roleId;
    private Long menuId;

    private boolean canView;
    private boolean canCreate;
    private boolean canUpdate;
    private boolean canDelete;
}