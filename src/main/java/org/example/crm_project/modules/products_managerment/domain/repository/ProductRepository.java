package org.example.crm_project.modules.products_managerment.domain.repository;

import org.example.crm_project.modules.products_managerment.domain.entity.Product;

import java.util.List;

public interface ProductRepository extends BaseRepository<Product, Long> {
    List<Product> search(String keyword, Long categoryId, Double minPrice, Double maxPrice, String status, int page, int size);
    long countSearch(String keyword, Long categoryId, Double minPrice, Double maxPrice, String status);
    boolean existsBySkuCode(String skuCode);
    boolean existsBySkuCodeExcludeId(String skuCode, Long id);
}
