package org.example.crm_project.modules.products_managerment.application.mapper;

import org.example.crm_project.modules.products_managerment.application.dto.request.CreateCategoryRequest;
import org.example.crm_project.modules.products_managerment.application.dto.request.UpdateCategoryRequest;
import org.example.crm_project.modules.products_managerment.application.dto.response.CategoryResponse;
import org.example.crm_project.modules.products_managerment.domain.entity.Category;

public class CategoryMapper {

    public static Category toEntity(CreateCategoryRequest req) {
        if (req == null)
            return null;
        return Category.create(req.getName(), req.getDescription(), null);
    }

    public static void updateEntity(UpdateCategoryRequest req, Category category) {
        if (req == null || category == null)
            return;
        category.update(req.getName(), req.getDescription(), null);
    }

    public static CategoryResponse toResponse(Category category) {
        if (category == null)
            return null;
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .isActive(category.getIsActive())
                .productCount(category.getProducts() != null ? category.getProducts().size() : 0)
                .build();
    }
}
