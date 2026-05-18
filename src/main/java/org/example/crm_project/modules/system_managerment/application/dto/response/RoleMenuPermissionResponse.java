package org.example.crm_project.modules.system_managerment.application.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleMenuPermissionResponse {

    private Long roleId;
    private Long menuId;

    private boolean canView;
    private boolean canCreate;
    private boolean canUpdate;
    private boolean canDelete;
}