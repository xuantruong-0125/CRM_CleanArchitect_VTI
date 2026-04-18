package org.example.crm_project.modules.system_managerment.application.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class MenuResponse {

    private Long id;
    private String name;
    private Long parentId;

    @Builder.Default
    private List<MenuResponse> children = new ArrayList<>();
}