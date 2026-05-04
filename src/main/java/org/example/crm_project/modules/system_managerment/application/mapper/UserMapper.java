package org.example.crm_project.modules.system_managerment.application.mapper;

import org.example.crm_project.modules.system_managerment.application.dto.response.UserResponse;
import org.example.crm_project.modules.system_managerment.domain.entity.User;
import org.example.crm_project.modules.system_managerment.infrastructure.persistence.entity.UserEntity;

public class UserMapper {

    public static User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return User.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .fullName(entity.getFullName())
                .roleId(entity.getRoleId())
                .organizationId(entity.getOrganizationId())
                .status(entity.getStatus())
                .build();
    }

    public static UserResponse toResponse(User domain) {
        if (domain == null) {
            return null;
        }
        return UserResponse.builder()
                .id(domain.getId())
                .username(domain.getUsername())
                .email(domain.getEmail())
                .fullName(domain.getFullName())
                .build();
    }
}
