package org.example.crm_project.modules.system_managerment.presentation.mapper;

import org.example.crm_project.modules.system_managerment.application.dto.request.*;
import org.example.crm_project.modules.system_managerment.application.dto.response.UserResponse;
import org.example.crm_project.modules.system_managerment.presentation.dto.request.*;
import org.example.crm_project.modules.system_managerment.presentation.dto.response.UserResponseDto;

public class UserPresentationMapper {

    public static CreateUserRequest toCreateRequest(CreateUserRequestDto dto) {
        CreateUserRequest req = new CreateUserRequest();
        req.setUsername(dto.getUsername());
        req.setPassword(dto.getPassword());
        req.setEmail(dto.getEmail());
        req.setFullName(dto.getFullName());
        req.setRoleId(dto.getRoleId());
        req.setOrganizationId(dto.getOrganizationId());
        return req;
    }

    public static UpdateUserRequest toUpdateRequest(UpdateUserRequestDto dto) {
        UpdateUserRequest req = new UpdateUserRequest();
        req.setEmail(dto.getEmail());
        req.setFullName(dto.getFullName());
        req.setRoleId(dto.getRoleId());
        req.setOrganizationId(dto.getOrganizationId());
        req.setStatus(dto.getStatus());
        return req;
    }

    public static ChangePasswordRequest toChangePassword(ChangePasswordRequestDto dto) {
        ChangePasswordRequest req = new ChangePasswordRequest();
        req.setOldPassword(dto.getOldPassword());
        req.setNewPassword(dto.getNewPassword());
        return req;
    }

    public static UserResponseDto toDto(UserResponse res) {
        return UserResponseDto.builder()
                .id(res.getId())
                .username(res.getUsername())
                .email(res.getEmail())
                .fullName(res.getFullName())
                .roleId(res.getRoleId())
                .organizationId(res.getOrganizationId())
                .status(res.getStatus())
                .lastLogin(res.getLastLogin())
                .createdAt(res.getCreatedAt())
                .build();
    }
}