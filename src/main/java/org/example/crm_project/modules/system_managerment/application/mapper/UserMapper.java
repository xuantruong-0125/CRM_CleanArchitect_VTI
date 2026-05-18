package org.example.crm_project.modules.system_managerment.application.mapper;

import org.example.crm_project.modules.system_managerment.application.dto.response.UserResponse;
import org.example.crm_project.modules.system_managerment.domain.entity.User;

public class UserMapper {

    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .roleId(user.getRoleId())
                .organizationId(user.getOrganizationId())
                .status(user.getStatus().name())
                .lastLogin(user.getLastLogin())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
