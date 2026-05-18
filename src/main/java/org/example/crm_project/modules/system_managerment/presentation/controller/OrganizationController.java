package org.example.crm_project.modules.system_managerment.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.system_managerment.application.dto.request.CreateOrganizationRequest;
import org.example.crm_project.modules.system_managerment.application.dto.request.UpdateOrganizationRequest;
import org.example.crm_project.modules.system_managerment.application.dto.response.OrganizationResponse;
import org.example.crm_project.modules.system_managerment.application.service.OrganizationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService service;

    @PostMapping
    public OrganizationResponse create(@RequestBody CreateOrganizationRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public OrganizationResponse update(@PathVariable Long id,
                                       @RequestBody UpdateOrganizationRequest req) {
        return service.update(id, req);
    }

    @GetMapping("/{id}")
    public OrganizationResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<OrganizationResponse> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/tree")
    public List<OrganizationResponse> getTree() {
        return service.getTree();
    }
}