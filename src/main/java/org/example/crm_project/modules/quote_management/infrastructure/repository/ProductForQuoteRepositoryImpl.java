package org.example.crm_project.modules.quote_management.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.products_managerment.infrastructure.persistence.entity.PriceEntity;
import org.example.crm_project.modules.products_managerment.infrastructure.persistence.entity.ProductEntity;
import org.example.crm_project.modules.quote_management.application.dto.response.QuoteFormProductResponse;
import org.example.crm_project.modules.quote_management.domain.repository.ProductForQuoteRepository;
import org.example.crm_project.modules.quote_management.infrastructure.persistence.repository.JpaProductForQuoteRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductForQuoteRepositoryImpl implements ProductForQuoteRepository {

    private final JpaProductForQuoteRepository jpaRepository;

    @Override
    public List<QuoteFormProductResponse> findAllActiveForForm() {
        return jpaRepository.findAllActiveForQuoteForm().stream()
                .map(this::toFormResponse)
                .toList();
    }

    private QuoteFormProductResponse toFormResponse(ProductEntity entity) {
        LocalDateTime now = LocalDateTime.now();
        List<PriceEntity> prices = entity.getPrices() != null ? entity.getPrices() : Collections.emptyList();
        BigDecimal finalPrice = prices.stream()
                .filter(p -> Boolean.TRUE.equals(p.getIsActive())
                        && p.getDeletedAt() == null
                        && p.getEffectiveFrom() != null
                        && !p.getEffectiveFrom().isAfter(now)
                        && (p.getEffectiveTo() == null || p.getEffectiveTo().isAfter(now)))
                .map(PriceEntity::getFinalPrice)
                .findFirst()
                .orElse(null);

        return QuoteFormProductResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .skuCode(entity.getSkuCode())
                .finalPrice(finalPrice)
                .build();
    }
}
