package org.example.crm_project.modules.system_managerment.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.system_managerment.application.dto.request.CreateMenuRequest;
import org.example.crm_project.modules.system_managerment.application.dto.request.UpdateMenuRequest;
import org.example.crm_project.modules.system_managerment.application.dto.response.MenuResponse;
import org.example.crm_project.modules.system_managerment.application.service.MenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService service;

    // ===== CREATE =====
    @PostMapping
    public MenuResponse create(@RequestBody CreateMenuRequest req) {
        return service.create(req);
    }

    // ===== UPDATE =====
    @PutMapping("/{id}")
    public MenuResponse update(@PathVariable Long id,
                               @RequestBody UpdateMenuRequest req) {
        return service.update(id, req);
    }

    // ===== GET ALL =====
    @GetMapping
    public List<MenuResponse> getAll() {
        return service.getAll();
    }

    // ===== GET TREE =====
    @GetMapping("/tree")
    public List<MenuResponse> getTree() {
        return service.getTree();
    }

    // ===== DELETE =====
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}