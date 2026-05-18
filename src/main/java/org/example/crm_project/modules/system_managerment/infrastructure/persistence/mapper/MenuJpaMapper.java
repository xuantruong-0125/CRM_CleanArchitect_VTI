package org.example.crm_project.modules.system_managerment.infrastructure.persistence.mapper;

import org.example.crm_project.modules.system_managerment.domain.entity.Menu;
import org.example.crm_project.modules.system_managerment.infrastructure.persistence.entity.MenuEntity;

public class MenuJpaMapper {

    public static MenuEntity toEntity(Menu menu) {
        MenuEntity e = new MenuEntity();
        e.setId(menu.getId()); // 🔥 cực quan trọng (update)
        e.setName(menu.getName());
        e.setParentId(menu.getParentId());
        return e;
    }

    public static Menu toDomain(MenuEntity e) {
        return new Menu(
                e.getId(),
                e.getName(),
                e.getParentId()
        );
    }
}