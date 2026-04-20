package org.example.crm_project.modules.quote_management.infrastructure.persistence.mapper;

import org.example.crm_project.modules.quote_management.domain.constant.ProductType;
import org.example.crm_project.modules.quote_management.domain.entity.Product;
import org.example.crm_project.modules.quote_management.infrastructure.persistence.entity.ProductEntity;

/**
 * JPA Mapper: chuyển đổi giữa ProductEntity (infrastructure) và Product (domain).
 */
public class ProductJpaMapper {

    public static Product toDomain(ProductEntity e) {
        return new Product(
                e.getId(),
                e.getSkuCode(),
                e.getName(),
                e.getType() != null ? ProductType.valueOf(e.getType()) : null,
                e.getIsActive()
        );
    }
}
