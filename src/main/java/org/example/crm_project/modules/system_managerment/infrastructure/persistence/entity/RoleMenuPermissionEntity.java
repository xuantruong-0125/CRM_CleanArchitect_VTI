package org.example.crm_project.modules.system_managerment.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "role_menu_permissions")
@Getter
@Setter
public class RoleMenuPermissionEntity {

    @EmbeddedId
    private RoleMenuPermissionId id;

    @Column(name = "can_view")
    private boolean canView;

    @Column(name = "can_create")
    private boolean canCreate;

    @Column(name = "can_update")
    private boolean canUpdate;

    @Column(name = "can_delete")
    private boolean canDelete;
}