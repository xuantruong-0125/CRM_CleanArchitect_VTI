package org.example.crm_project.modules.system_managerment.application.mapper;

import org.example.crm_project.modules.system_managerment.application.dto.request.CreateMenuRequest;
import org.example.crm_project.modules.system_managerment.application.dto.response.MenuResponse;
import org.example.crm_project.modules.system_managerment.domain.entity.Menu;

public class MenuMapper {

    public static Menu toEntity(CreateMenuRequest req) {
        return new Menu(req.getName(), req.getParentId());
    }

    public static MenuResponse toResponse(Menu menu) {
        return MenuResponse.builder()
                .id(menu.getId())
                .name(menu.getName())
                .parentId(menu.getParentId())
                .build();
    }
}