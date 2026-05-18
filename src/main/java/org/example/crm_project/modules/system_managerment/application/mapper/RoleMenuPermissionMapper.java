package org.example.crm_project.modules.system_managerment.application.mapper;

import org.example.crm_project.modules.system_managerment.application.dto.response.RoleMenuPermissionResponse;
import org.example.crm_project.modules.system_managerment.domain.entity.RoleMenuPermission;

public class RoleMenuPermissionMapper {

    public static RoleMenuPermissionResponse toResponse(RoleMenuPermission p) {
        return RoleMenuPermissionResponse.builder()
                .roleId(p.getRoleId())
                .menuId(p.getMenuId())
                .canView(p.isCanView())
                .canCreate(p.isCanCreate())
                .canUpdate(p.isCanUpdate())
                .canDelete(p.isCanDelete())
                .build();
    }
}