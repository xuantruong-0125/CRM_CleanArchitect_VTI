package org.example.crm_project.modules.system_managerment.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.system_managerment.application.dto.request.CreateRoleRequest;
import org.example.crm_project.modules.system_managerment.application.dto.request.UpdateRoleRequest;
import org.example.crm_project.modules.system_managerment.application.dto.response.RoleResponse;
import org.example.crm_project.modules.system_managerment.application.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService service;

    // ===== CREATE =====
    @PostMapping
    public RoleResponse create(@RequestBody CreateRoleRequest req) {
        return service.create(req);
    }

    // ===== UPDATE =====
    @PutMapping("/{id}")
    public RoleResponse update(@PathVariable Long id,
                               @RequestBody UpdateRoleRequest req) {
        return service.update(id, req);
    }

    // ===== GET ALL =====
    @GetMapping
    public List<RoleResponse> getAll() {
        return service.getAll();
    }

    // ===== GET BY ID =====
    @GetMapping("/{id}")
    public RoleResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // ===== DELETE =====
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}