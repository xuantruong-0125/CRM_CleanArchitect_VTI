package org.example.crm_project.modules.system_managerment.infrastructure.persistence.repository;

import org.example.crm_project.modules.system_managerment.infrastructure.persistence.entity.RoleMenuPermissionEntity;
import org.example.crm_project.modules.system_managerment.infrastructure.persistence.entity.RoleMenuPermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaRoleMenuPermissionRepository
        extends JpaRepository<RoleMenuPermissionEntity, RoleMenuPermissionId> {

    Optional<RoleMenuPermissionEntity> findByIdRoleIdAndIdMenuId(Long roleId, Long menuId);

    List<RoleMenuPermissionEntity> findByIdRoleId(Long roleId);

    void deleteByIdRoleIdAndIdMenuId(Long roleId, Long menuId);

    void deleteByIdRoleId(Long roleId);
}