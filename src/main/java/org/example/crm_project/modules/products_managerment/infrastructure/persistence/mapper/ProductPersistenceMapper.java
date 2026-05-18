package org.example.crm_project.modules.products_managerment.infrastructure.persistence.mapper;

import org.example.crm_project.modules.products_managerment.domain.entity.Product;
import org.example.crm_project.modules.products_managerment.infrastructure.persistence.entity.ProductEntity;

public class ProductPersistenceMapper {

    public static ProductEntity toEntity(Product domain) {
        if (domain == null) return null;
        ProductEntity entity = new ProductEntity();
        entity.setId(domain.getId());
        entity.setSkuCode(domain.getSkuCode());
        entity.setName(domain.getName());
        entity.setImageUrl(domain.getImageUrl());
        entity.setDescription(domain.getDescription());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        entity.setDeletedAt(domain.getDeletedAt());
        entity.setCreatedBy(domain.getCreatedBy());
        entity.setUpdatedBy(domain.getUpdatedBy());
        entity.setIsActive(domain.getIsActive());
        
        if (domain.getCategory() != null) {
            entity.setCategoryEntity(CategoryPersistenceMapper.toEntity(domain.getCategory()));
        }
        
        return entity;
    }

    public static Product toDomain(ProductEntity entity) {
        if (entity == null) return null;
        Product domain = new Product();
        domain.setId(entity.getId());
        domain.setSkuCode(entity.getSkuCode());
        domain.setName(entity.getName());
        domain.setImageUrl(entity.getImageUrl());
        domain.setDescription(entity.getDescription());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        domain.setDeletedAt(entity.getDeletedAt());
        domain.setCreatedBy(entity.getCreatedBy());
        domain.setUpdatedBy(entity.getUpdatedBy());
        domain.setIsActive(entity.getIsActive());
        domain.setCategory(CategoryPersistenceMapper.toDomain(entity.getCategoryEntity()));
        
        if (entity.getPrices() != null) {
            entity.getPrices().forEach(priceEntity -> 
                domain.addPrice(PricePersistenceMapper.toDomainWithoutProduct(priceEntity))
            );
        }
        
        return domain;
    }
}
