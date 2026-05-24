package org.example.crm_project.modules.orders.infrastructure.persistence.repository;

import org.example.crm_project.modules.orders.infrastructure.persistence.entity.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, Long> {
}