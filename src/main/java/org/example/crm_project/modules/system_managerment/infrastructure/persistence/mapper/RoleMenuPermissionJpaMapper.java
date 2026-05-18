package org.example.crm_project.modules.system_managerment.infrastructure.persistence.mapper;

import org.example.crm_project.modules.system_managerment.domain.entity.RoleMenuPermission;
import org.example.crm_project.modules.system_managerment.infrastructure.persistence.entity.RoleMenuPermissionEntity;
import org.example.crm_project.modules.system_managerment.infrastructure.persistence.entity.RoleMenuPermissionId;

public class RoleMenuPermissionJpaMapper {

    // ===== DOMAIN → ENTITY =====
    public static RoleMenuPermissionEntity toEntity(RoleMenuPermission p) {

        RoleMenuPermissionEntity e = new RoleMenuPermissionEntity();

        e.setId(new RoleMenuPermissionId(
                p.getRoleId(),
                p.getMenuId()
        ));

        e.setCanView(p.isCanView());
        e.setCanCreate(p.isCanCreate());
        e.setCanUpdate(p.isCanUpdate());
        e.setCanDelete(p.isCanDelete());

        return e;
    }

    // ===== ENTITY → DOMAIN =====
    public static RoleMenuPermission toDomain(RoleMenuPermissionEntity e) {

        return new RoleMenuPermission(
                e.getId().getRoleId(),
                e.getId().getMenuId(),
                e.isCanView(),
                e.isCanCreate(),
                e.isCanUpdate(),
                e.isCanDelete()
        );
    }
}