package org.example.crm_project.modules.products_managerment.application.mapper;

import org.example.crm_project.modules.products_managerment.application.dto.request.CreateProductRequest;
import org.example.crm_project.modules.products_managerment.application.dto.request.UpdateProductRequest;
import org.example.crm_project.modules.products_managerment.application.dto.response.ProductResponse;
import org.example.crm_project.modules.products_managerment.domain.entity.Price;
import org.example.crm_project.modules.products_managerment.domain.entity.Product;

import java.math.BigDecimal;

public class ProductMapper {

    public static Product toEntity(CreateProductRequest req) {
        if (req == null) return null;
        return Product.create(
                req.getSkuCode(),
                req.getName(),
                req.getImageUrl(),
                req.getDescription(),
                null,
                null
        );
    }

    public static void updateEntity(UpdateProductRequest req, Product product) {
        if (req == null || product == null) return;
        product.update(
                req.getName(),
                req.getImageUrl(),
                req.getDescription(),
                null
        );
    }

    public static ProductResponse toResponse(Product product) {
        if (product == null) return null;
        
        BigDecimal currentFinalPrice = product.getPrices().stream()
                .filter(Price::isCurrentlyEffective)
                .map(Price::getFinalPrice)
                .findFirst()
                .orElse(null);

        return ProductResponse.builder()
                .id(product.getId())
                .skuCode(product.getSkuCode())
                .name(product.getName())
                .imageUrl(product.getImageUrl())
                .description(product.getDescription())
                .finalPrice(currentFinalPrice)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .isActive(product.getIsActive())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .build();
    }
}
