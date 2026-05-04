package org.example.crm_project.modules.system_managerment.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String email;
    private String fullName;
    private Integer roleId;
    private Integer organizationId;
    private String status;
}
