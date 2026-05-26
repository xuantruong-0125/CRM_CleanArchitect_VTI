package org.example.crm_project.modules.products_managerment.infrastructure.persistence.mapper;

import org.example.crm_project.modules.products_managerment.domain.entity.Price;
import org.example.crm_project.modules.products_managerment.infrastructure.persistence.entity.PriceEntity;

public class PricePersistenceMapper {

    public static PriceEntity toEntity(Price domain) {
        if (domain == null)
            return null;
        PriceEntity entity = new PriceEntity();
        entity.setId(domain.getId());
        entity.setBasePrice(domain.getBasePrice());
        entity.setTaxRate(domain.getTaxRate());
        entity.setEffectiveFrom(domain.getEffectiveFrom());
        entity.setEffectiveTo(domain.getEffectiveTo());
        entity.setFinalPrice(domain.getFinalPrice());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        entity.setDeletedAt(domain.getDeletedAt());
        entity.setCreatedBy(domain.getCreatedBy());
        entity.setUpdatedBy(domain.getUpdatedBy());
        entity.setIsActive(domain.getActive());

        if (domain.getProduct() != null) {
            entity.setProductEntity(ProductPersistenceMapper.toEntity(domain.getProduct()));
        }

        return entity;
    }

    public static Price toDomain(PriceEntity entity) {
        if (entity == null)
            return null;
        Price domain = toDomainWithoutProduct(entity);
        domain.setProduct(ProductPersistenceMapper.toDomain(entity.getProductEntity()));
        return domain;
    }

    public static Price toDomainWithoutProduct(PriceEntity entity) {
        if (entity == null)
            return null;
        Price domain = new Price();
        domain.setId(entity.getId());
        domain.setBasePrice(entity.getBasePrice());
        domain.setTaxRate(entity.getTaxRate());
        domain.setEffectiveFrom(entity.getEffectiveFrom());
        domain.setEffectiveTo(entity.getEffectiveTo());
        domain.setFinalPrice(entity.getFinalPrice());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        domain.setDeletedAt(entity.getDeletedAt());
        domain.setCreatedBy(entity.getCreatedBy());
        domain.setUpdatedBy(entity.getUpdatedBy());
        domain.setActive(entity.getIsActive());
        return domain;
    }
}
