package org.example.crm_project.modules.system_managerment.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.system_managerment.application.dto.request.UpsertRoleMenuPermissionRequest;
import org.example.crm_project.modules.system_managerment.application.dto.response.RoleMenuPermissionResponse;
import org.example.crm_project.modules.system_managerment.application.service.RoleMenuPermissionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class RoleMenuPermissionController {

    private final RoleMenuPermissionService service;

    // ===== UPSERT =====
    @PostMapping
    public RoleMenuPermissionResponse upsert(
            @RequestBody UpsertRoleMenuPermissionRequest req
    ) {
        return service.upsert(req);
    }

    // ===== GET BY ROLE =====
    @GetMapping("/role/{roleId}")
    public List<RoleMenuPermissionResponse> getByRole(
            @PathVariable Long roleId
    ) {
        return service.getByRole(roleId);
    }

    // ===== DELETE ONE =====
    @DeleteMapping
    public void delete(
            @RequestParam Long roleId,
            @RequestParam Long menuId
    ) {
        service.delete(roleId, menuId);
    }

    // ===== DELETE ALL OF ROLE =====
    @DeleteMapping("/role/{roleId}")
    public void deleteByRole(
            @PathVariable Long roleId
    ) {
        service.deleteByRole(roleId);
    }
}