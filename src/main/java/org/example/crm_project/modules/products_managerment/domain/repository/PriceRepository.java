package org.example.crm_project.modules.products_managerment.domain.repository;

import org.example.crm_project.modules.products_managerment.domain.entity.Price;

import java.util.List;

public interface PriceRepository extends BaseRepository<Price, Long> {
    List<Price> findByProductId(Long productId);

    List<Price> search(String keyword, Long productId, int page, int size);

    long countSearch(String keyword, Long productId);
}
