package org.example.crm_project.modules.products_managerment.application.mapper;

import org.example.crm_project.modules.products_managerment.application.dto.request.CreatePriceRequest;
import org.example.crm_project.modules.products_managerment.application.dto.request.UpdatePriceRequest;
import org.example.crm_project.modules.products_managerment.application.dto.response.PriceResponse;
import org.example.crm_project.modules.products_managerment.domain.entity.Price;

public class PriceMapper {

    public static Price toEntity(CreatePriceRequest req) {
        if (req == null) return null;
        return Price.create(
                req.getBasePrice(),
                req.getTaxRate(),
                req.getEffectiveFrom(),
                req.getEffectiveTo(),
                null,
                null
        );
    }

    public static void updateEntity(UpdatePriceRequest req, Price price) {
        if (req == null || price == null) return;
        price.setBasePrice(req.getBasePrice());
        price.setTaxRate(req.getTaxRate());
        price.setEffectiveFrom(req.getEffectiveFrom());
        price.setEffectiveTo(req.getEffectiveTo());
    }

    public static PriceResponse toResponse(Price price) {
        if (price == null) return null;
        return PriceResponse.builder()
                .id(price.getId())
                .basePrice(price.getBasePrice())
                .taxRate(price.getTaxRate())
                .effectiveFrom(price.getEffectiveFrom())
                .effectiveTo(price.getEffectiveTo())
                .finalPrice(price.getFinalPrice())
                .createdAt(price.getCreatedAt())
                .updatedAt(price.getUpdatedAt())
                .isActive(price.getActive())
                .productId(price.getProduct() != null ? price.getProduct().getId() : null)
                .productName(price.getProduct() != null ? price.getProduct().getName() : null)
                .productSkuCode(price.getProduct() != null ? price.getProduct().getSkuCode() : null)
                .productImageUrl(price.getProduct() != null ? price.getProduct().getImageUrl() : null)
                .build();
    }
}
