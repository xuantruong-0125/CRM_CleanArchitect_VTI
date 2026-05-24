package org.example.crm_project.modules.orders.domain.repository;

import org.example.crm_project.modules.orders.domain.entity.Order;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(Long id);
    void deleteById(Long id);
}