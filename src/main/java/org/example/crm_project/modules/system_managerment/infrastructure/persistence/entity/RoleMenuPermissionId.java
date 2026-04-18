package org.example.crm_project.modules.system_managerment.infrastructure.persistence.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RoleMenuPermissionId implements Serializable {

    private Long roleId;
    private Long menuId;
}