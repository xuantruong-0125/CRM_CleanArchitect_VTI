package org.example.crm_project.modules.system_managerment.application.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String fullName;

    private Long roleId;
    private Long organizationId;

    private String status;

    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
}