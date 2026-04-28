package org.example.crm_project.modules.products_managerment.application.dto.request;

import lombok.Data;

@Data
public class CreateProductRequest {
    private String skuCode;
    private String name;
    private String imageUrl;
    private String description;
    private Long categoryId;
}
