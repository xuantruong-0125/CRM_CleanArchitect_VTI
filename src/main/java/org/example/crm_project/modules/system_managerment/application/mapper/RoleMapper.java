package org.example.crm_project.modules.system_managerment.application.mapper;

import org.example.crm_project.modules.system_managerment.application.dto.request.CreateRoleRequest;
import org.example.crm_project.modules.system_managerment.application.dto.response.RoleResponse;
import org.example.crm_project.modules.system_managerment.domain.constant.RoleScope;
import org.example.crm_project.modules.system_managerment.domain.entity.Role;

public class RoleMapper {

    public static Role toEntity(CreateRoleRequest req) {
        return new Role(
                req.getName(),
                req.getDescription(),
                RoleScope.valueOf(req.getScope()) // 🔥 convert String → Enum
        );
    }

    public static RoleResponse toResponse(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .scope(role.getScope().name())
                .build();
    }
}