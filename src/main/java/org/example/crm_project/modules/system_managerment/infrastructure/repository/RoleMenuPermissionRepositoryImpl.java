package org.example.crm_project.modules.system_managerment.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.system_managerment.domain.entity.RoleMenuPermission;
import org.example.crm_project.modules.system_managerment.domain.repository.RoleMenuPermissionRepository;
import org.example.crm_project.modules.system_managerment.infrastructure.persistence.mapper.RoleMenuPermissionJpaMapper;
import org.example.crm_project.modules.system_managerment.infrastructure.persistence.repository.JpaRoleMenuPermissionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleMenuPermissionRepositoryImpl implements RoleMenuPermissionRepository {

    private final JpaRoleMenuPermissionRepository jpaRepository;

    @Override
    public RoleMenuPermission save(RoleMenuPermission permission) {
        return RoleMenuPermissionJpaMapper.toDomain(
                jpaRepository.save(
                        RoleMenuPermissionJpaMapper.toEntity(permission)
                )
        );
    }

    @Override
    public Optional<RoleMenuPermission> findByRoleIdAndMenuId(Long roleId, Long menuId) {
        return jpaRepository
                .findByIdRoleIdAndIdMenuId(roleId, menuId)
                .map(RoleMenuPermissionJpaMapper::toDomain);
    }

    @Override
    public List<RoleMenuPermission> findByRoleId(Long roleId) {
        return jpaRepository.findByIdRoleId(roleId)
                .stream()
                .map(RoleMenuPermissionJpaMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteByRoleIdAndMenuId(Long roleId, Long menuId) {
        jpaRepository.deleteByIdRoleIdAndIdMenuId(roleId, menuId);
    }

    @Override
    public void deleteByRoleId(Long roleId) {
        jpaRepository.deleteByIdRoleId(roleId);
    }
}