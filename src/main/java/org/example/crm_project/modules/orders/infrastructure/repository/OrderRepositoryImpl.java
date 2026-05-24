package org.example.crm_project.modules.orders.infrastructure.repository;

import org.example.crm_project.modules.orders.domain.entity.Order;
import org.example.crm_project.modules.orders.domain.repository.OrderRepository;
import org.example.crm_project.modules.orders.infrastructure.persistence.mapper.OrderPersistenceMapper;
import org.example.crm_project.modules.orders.infrastructure.persistence.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderJpaRepository jpaRepository;
    private final OrderPersistenceMapper mapper;

    @Override
    public Order save(Order order) {
        var entity = mapper.toJpaEntity(order);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomainEntity(savedEntity);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomainEntity);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}