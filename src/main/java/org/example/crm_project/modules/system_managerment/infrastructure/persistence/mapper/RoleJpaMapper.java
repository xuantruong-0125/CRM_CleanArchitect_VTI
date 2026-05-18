package org.example.crm_project.modules.system_managerment.infrastructure.persistence.mapper;

import org.example.crm_project.modules.system_managerment.domain.constant.RoleScope;
import org.example.crm_project.modules.system_managerment.domain.entity.Role;
import org.example.crm_project.modules.system_managerment.infrastructure.persistence.entity.RoleEntity;

public class RoleJpaMapper {

    // ===== DOMAIN → ENTITY =====
    public static RoleEntity toEntity(Role role) {
        RoleEntity e = new RoleEntity();
        e.setId(role.getId()); // 🔥 cực kỳ quan trọng (update)
        e.setName(role.getName());
        e.setDescription(role.getDescription());
        e.setScope(role.getScope().name());
        return e;
    }

    // ===== ENTITY → DOMAIN =====
    public static Role toDomain(RoleEntity e) {
        return new Role(
                e.getId(),
                e.getName(),
                e.getDescription(),
                RoleScope.valueOf(e.getScope())
        );
    }
}