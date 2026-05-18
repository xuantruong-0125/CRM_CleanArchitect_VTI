package org.example.crm_project.modules.system_managerment.domain.repository;

import org.example.crm_project.modules.system_managerment.domain.entity.RoleMenuPermission;

import java.util.List;
import java.util.Optional;

public interface RoleMenuPermissionRepository {

    RoleMenuPermission save(RoleMenuPermission permission);

    Optional<RoleMenuPermission> findByRoleIdAndMenuId(Long roleId, Long menuId);

    List<RoleMenuPermission> findByRoleId(Long roleId);

    void deleteByRoleIdAndMenuId(Long roleId, Long menuId);

    void deleteByRoleId(Long roleId);
}