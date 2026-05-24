package org.example.crm_project.modules.orders.infrastructure.persistence.mapper;

import org.example.crm_project.modules.orders.domain.entity.Order;
import org.example.crm_project.modules.orders.domain.entity.OrderLineItem;
import org.example.crm_project.modules.orders.infrastructure.persistence.entity.OrderJpaEntity;
import org.example.crm_project.modules.orders.infrastructure.persistence.entity.OrderLineItemJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderPersistenceMapper {

    public OrderJpaEntity toJpaEntity(Order domain) {
        if (domain == null) return null;

        OrderJpaEntity entity = new OrderJpaEntity();
        entity.setId(domain.getId());
        entity.setOrderNumber(domain.getOrderNumber());
        entity.setCustomerId(domain.getCustomerId());
        entity.setOpportunityId(domain.getOpportunityId());
        entity.setTotalAmount(domain.getTotalAmount());
        entity.setCurrencyCode(domain.getCurrencyCode());
        entity.setExchangeRate(domain.getExchangeRate());
        entity.setStatus(domain.getStatus());

        if (domain.getLineItems() != null) {
            domain.getLineItems().forEach(item -> {
                OrderLineItemJpaEntity itemEntity = new OrderLineItemJpaEntity();
                itemEntity.setId(item.getId());
                itemEntity.setProductId(item.getProductId());
                itemEntity.setQuantity(item.getQuantity());
                itemEntity.setUnitPrice(item.getUnitPrice());
                entity.addLineItem(itemEntity);
            });
        }
        return entity;
    }

    public Order toDomainEntity(OrderJpaEntity entity) {
        if (entity == null) return null;

        List<OrderLineItem> domainItems = entity.getLineItems().stream()
                .map(item -> OrderLineItem.builder()
                        .id(item.getId())
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .build())
                .collect(Collectors.toList());

        return Order.builder()
                .id(entity.getId())
                .orderNumber(entity.getOrderNumber())
                .customerId(entity.getCustomerId())
                .opportunityId(entity.getOpportunityId())
                .totalAmount(entity.getTotalAmount())
                .currencyCode(entity.getCurrencyCode())
                .exchangeRate(entity.getExchangeRate())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .lineItems(domainItems)
                .build();
    }
}