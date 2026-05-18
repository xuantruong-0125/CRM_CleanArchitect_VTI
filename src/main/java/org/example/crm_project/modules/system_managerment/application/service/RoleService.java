package org.example.crm_project.modules.system_managerment.application.service;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.system_managerment.application.dto.request.CreateRoleRequest;
import org.example.crm_project.modules.system_managerment.application.dto.request.UpdateRoleRequest;
import org.example.crm_project.modules.system_managerment.application.dto.response.RoleResponse;
import org.example.crm_project.modules.system_managerment.application.mapper.RoleMapper;
import org.example.crm_project.modules.system_managerment.domain.constant.RoleScope;
import org.example.crm_project.modules.system_managerment.domain.entity.Role;
import org.example.crm_project.modules.system_managerment.domain.exception.RoleNotFoundException;
import org.example.crm_project.modules.system_managerment.domain.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    // ===== CREATE =====
    public RoleResponse create(CreateRoleRequest req) {

        // 🔥 check duplicate name
        if (repository.existsByName(req.getName())) {
            throw new RuntimeException("Role name already exists");
        }

        Role role = RoleMapper.toEntity(req);

        return RoleMapper.toResponse(repository.save(role));
    }

    // ===== UPDATE =====
    public RoleResponse update(Long id, UpdateRoleRequest req) {

        Role role = repository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(id));

        if (req.getName() != null && !req.getName().equals(role.getName())) {
            if (repository.existsByName(req.getName())) {
                throw new RuntimeException("Role name already exists");
            }
            role.changeName(req.getName());
        }

        if (req.getDescription() != null) {
            role.changeDescription(req.getDescription());
        }

        if (req.getScope() != null) {
            role.changeScope(RoleScope.valueOf(req.getScope()));
        }

        return RoleMapper.toResponse(repository.save(role));
    }

    // ===== GET ALL =====
    public List<RoleResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(RoleMapper::toResponse)
                .toList();
    }

    // ===== GET BY ID =====
    public RoleResponse getById(Long id) {
        Role role = repository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(id));

        return RoleMapper.toResponse(role);
    }

    // ===== DELETE =====
    public void delete(Long id) {

        Role role = repository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(id));

        // 🔥 rule: không cho xoá ADMIN
        if ("ADMIN".equalsIgnoreCase(role.getName())) {
            throw new RuntimeException("Cannot delete ADMIN role");
        }

        repository.deleteById(id);
    }
}