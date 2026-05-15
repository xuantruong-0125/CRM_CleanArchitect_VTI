package org.example.crm_project.modules.quote_management.domain.repository;

import org.example.crm_project.modules.quote_management.domain.entity.Product;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository interface cho Product (read-only trong module báo giá).
 */
public interface ProductRepository {

    List<Product> findAllActive();

    Optional<Product> findById(Integer id);
}
