package org.example.crm_project.modules.system_managerment.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.system_managerment.application.dto.request.UpsertRoleMenuPermissionRequest;
import org.example.crm_project.modules.system_managerment.application.dto.response.RoleMenuPermissionResponse;
import org.example.crm_project.modules.system_managerment.application.mapper.RoleMenuPermissionMapper;
import org.example.crm_project.modules.system_managerment.domain.entity.RoleMenuPermission;
import org.example.crm_project.modules.system_managerment.domain.exception.RoleMenuPermissionNotFoundException;
import org.example.crm_project.modules.system_managerment.domain.repository.RoleMenuPermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleMenuPermissionService {

    private final RoleMenuPermissionRepository repository;

    // ===== CREATE / UPDATE =====
    public RoleMenuPermissionResponse upsert(UpsertRoleMenuPermissionRequest req) {

        RoleMenuPermission permission = repository
                .findByRoleIdAndMenuId(req.getRoleId(), req.getMenuId())
                .orElse(new RoleMenuPermission(req.getRoleId(), req.getMenuId()));

        // 🔥 reset trước khi set lại (tránh giữ state cũ)
        permission.revokeView();

        if (req.isCanView()) permission.allowView();
        if (req.isCanCreate()) permission.allowCreate();
        if (req.isCanUpdate()) permission.allowUpdate();
        if (req.isCanDelete()) permission.allowDelete();

        return RoleMenuPermissionMapper.toResponse(
                repository.save(permission)
        );
    }

    // ===== GET BY ROLE =====
    public List<RoleMenuPermissionResponse> getByRole(Long roleId) {
        return repository.findByRoleId(roleId)
                .stream()
                .map(RoleMenuPermissionMapper::toResponse)
                .toList();
    }

    // ===== DELETE ONE =====
    @Transactional
    public void delete(Long roleId, Long menuId) {
        repository.findByRoleIdAndMenuId(roleId, menuId)
                .orElseThrow(() ->
                        new RoleMenuPermissionNotFoundException(roleId, menuId)
                );

        repository.deleteByRoleIdAndMenuId(roleId, menuId);
    }

    // ===== DELETE ALL OF ROLE =====
    @Transactional
    public void deleteByRole(Long roleId) {
        repository.deleteByRoleId(roleId);
    }
}