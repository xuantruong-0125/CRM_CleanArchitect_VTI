package org.example.crm_project.modules.system_managerment.application.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class OrganizationResponse {
    private Long id;
    private String name;
    private Long parentId;
    private String type;

    @Builder.Default
    private List<OrganizationResponse> children= new ArrayList<>(); // bổ sung để xây tree
}