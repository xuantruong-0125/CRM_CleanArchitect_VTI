package org.example.crm_project.modules.system_managerment.application.dto.request;
import lombok.Data;

@Data
public class CreateOrganizationRequest {
    private String name;
    private Long parentId;
    private String type;
}