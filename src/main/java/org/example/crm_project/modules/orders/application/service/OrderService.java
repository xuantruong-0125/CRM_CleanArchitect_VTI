package org.example.crm_project.modules.orders.application.service;

import org.example.crm_project.modules.orders.application.dto.request.CreateOrderRequest;
import org.example.crm_project.modules.orders.application.dto.response.OrderResponse;
import org.example.crm_project.modules.orders.domain.constant.OrderStatus;
import org.example.crm_project.modules.orders.domain.entity.Order;
import org.example.crm_project.modules.orders.domain.entity.OrderLineItem;
import org.example.crm_project.modules.orders.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        List<OrderLineItem> items = request.getItems().stream()
                .map(itemReq -> OrderLineItem.builder()
                        .productId(itemReq.getProductId())
                        .quantity(itemReq.getQuantity())
                        .unitPrice(itemReq.getUnitPrice())
                        .build())
                .collect(Collectors.toList());

        Order newOrder = Order.builder()
                .customerId(request.getCustomerId())
                .opportunityId(request.getOpportunityId())
                .currencyCode(request.getCurrencyCode() != null ? request.getCurrencyCode() : "VND")
                .status(OrderStatus.DRAFT)
                .lineItems(items)
                .build();

        newOrder.calculateTotalAmount();
        Order savedOrder = orderRepository.save(newOrder);

        return mapToResponse(savedOrder);
    }

    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với ID: " + id));
        return mapToResponse(order);
    }

    @Transactional
    public OrderResponse updateOrderStatus(Long id, OrderStatus newStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với ID: " + id));
        order.setStatus(newStatus);
        Order updatedOrder = orderRepository.save(order);
        return mapToResponse(updatedOrder);
    }

    @Transactional
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    private OrderResponse mapToResponse(Order order) {
        List<OrderResponse.OrderLineItemResponse> itemResponses = order.getLineItems().stream()
                .map(item -> OrderResponse.OrderLineItemResponse.builder()
                        .id(item.getId())
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .totalPrice(item.calculateTotalPrice())
                        .build())
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .customerId(order.getCustomerId())
                .totalAmount(order.getTotalAmount())
                .currencyCode(order.getCurrencyCode())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .lineItems(itemResponses)
                .build();
    }
}