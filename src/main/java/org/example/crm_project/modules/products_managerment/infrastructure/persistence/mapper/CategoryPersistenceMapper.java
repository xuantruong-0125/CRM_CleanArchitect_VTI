package org.example.crm_project.modules.products_managerment.infrastructure.persistence.mapper;

import org.example.crm_project.modules.products_managerment.domain.entity.Category;
import org.example.crm_project.modules.products_managerment.infrastructure.persistence.entity.CategoryEntity;

public class CategoryPersistenceMapper {

    public static CategoryEntity toEntity(Category domain) {
        if (domain == null)
            return null;
        CategoryEntity entity = new CategoryEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        entity.setDeletedAt(domain.getDeletedAt());
        entity.setCreatedBy(domain.getCreatedBy());
        entity.setUpdatedBy(domain.getUpdatedBy());
        entity.setIsActive(domain.getIsActive());
        return entity;
    }

    public static Category toDomain(CategoryEntity entity) {
        if (entity == null)
            return null;
        Category domain = new Category();
        domain.setId(entity.getId());
        domain.setName(entity.getName());
        domain.setDescription(entity.getDescription());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        domain.setDeletedAt(entity.getDeletedAt());
        domain.setCreatedBy(entity.getCreatedBy());
        domain.setUpdatedBy(entity.getUpdatedBy());
        domain.setIsActive(entity.getIsActive());
        return domain;
    }
}
