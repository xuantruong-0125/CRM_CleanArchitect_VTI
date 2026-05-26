package org.example.crm_project.modules.products_managerment.application.dto.request;

import lombok.Data;

@Data
public class CreateCategoryRequest {
    private String name;
    private String description;
}
