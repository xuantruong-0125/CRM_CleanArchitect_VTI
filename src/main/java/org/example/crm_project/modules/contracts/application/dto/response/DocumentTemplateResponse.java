package org.example.crm_project.modules.contracts.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DocumentTemplateResponse {
    private Long id;
    private String name;
    private String type;
    private Boolean isActive;
}
