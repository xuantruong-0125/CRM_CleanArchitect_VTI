package org.example.crm_project.modules.system_managerment.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.system_managerment.application.dto.request.UpsertRoleMenuPermissionRequest;
import org.example.crm_project.modules.system_managerment.application.dto.response.RoleMenuPermissionResponse;
import org.example.crm_project.modules.system_managerment.application.mapper.RoleMenuPermissionMapper;
import org.example.crm_project.modules.system_managerment.domain.entity.RoleMenuPermission;
import org.example.crm_project.modules.system_managerment.domain.exception.RoleMenuPermissionNotFoundException;
import org.example.crm_project.modules.system_managerment.domain.repository.MenuRepository;
import org.example.crm_project.modules.system_managerment.domain.repository.RoleMenuPermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleMenuPermissionService {

    private final RoleMenuPermissionRepository repository;
    private final MenuRepository menuRepository;

    // ===== CREATE / UPDATE =====
    @Transactional
    public void upsertBatch(UpsertRoleMenuPermissionRequest req) {

        Long roleId = req.getRoleId();

        // 🔥 load tất cả permission hiện có (tránh N+1 query)
        var existing = repository.findByRoleId(roleId);

        var permissionMap = existing.stream()
                .collect(Collectors.toMap(
                        RoleMenuPermission::getMenuId,
                        p -> p
                ));

        List<RoleMenuPermission> toSave = new java.util.ArrayList<>();

        for (var item : req.getPermissions()) {

            RoleMenuPermission permission = permissionMap.getOrDefault(
                    item.getMenuId(),
                    new RoleMenuPermission(roleId, item.getMenuId())
            );

            // reset state
            permission.revokeView();

            if (item.isCanView()) permission.allowView();
            if (item.isCanCreate()) permission.allowCreate();
            if (item.isCanUpdate()) permission.allowUpdate();
            if (item.isCanDelete()) permission.allowDelete();

            toSave.add(permission);
        }

        repository.saveAll(toSave);
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

    public List<RoleMenuPermissionResponse> getFullByRole(Long roleId) {

        // 🔥 1. lấy toàn bộ menu
        var menus = menuRepository.findAll();

        // 🔥 2. lấy permission theo role
        var permissions = repository.findByRoleId(roleId);

        // 🔥 3. map permission theo menuId
        var permissionMap = permissions.stream()
                .collect(Collectors.toMap(
                        RoleMenuPermission::getMenuId,
                        p -> p
                ));

        // 🔥 4. build response
        return menus.stream()
                .map(menu -> {

                    var p = permissionMap.get(menu.getId());

                    return RoleMenuPermissionResponse.builder()
                            .roleId(roleId)
                            .menuId(menu.getId())
                            .menuName(menu.getName())
                            .parentId(menu.getParentId())
                            .canView(p != null && p.isCanView())
                            .canCreate(p != null && p.isCanCreate())
                            .canUpdate(p != null && p.isCanUpdate())
                            .canDelete(p != null && p.isCanDelete())
                            .build();
                })
                .toList();
    }


}