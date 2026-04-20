package org.example.crm_project.modules.quote_management.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.quote_management.domain.entity.Product;
import org.example.crm_project.modules.quote_management.domain.repository.ProductRepository;
import org.example.crm_project.modules.quote_management.infrastructure.persistence.mapper.ProductJpaMapper;
import org.example.crm_project.modules.quote_management.infrastructure.persistence.repository.JpaProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation của ProductRepository (domain interface).
 */
@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final JpaProductRepository jpaRepository;

    @Override
    public List<Product> findAllActive() {
        return jpaRepository.findAllActive()
                .stream()
                .map(ProductJpaMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return jpaRepository.findById(id)
                .map(ProductJpaMapper::toDomain);
    }
}
