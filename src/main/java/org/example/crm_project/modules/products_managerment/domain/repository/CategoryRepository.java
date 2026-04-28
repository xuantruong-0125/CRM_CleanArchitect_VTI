package org.example.crm_project.modules.products_managerment.domain.repository;

import org.example.crm_project.modules.products_managerment.domain.entity.Category;

import java.util.List;
import java.util.Map;

public interface CategoryRepository extends BaseRepository<Category, Long> {
    List<Category> search(String keyword, int page, int size);
    long countSearch(String keyword);
    long countActive();
    Map<Long, Integer> getProductCounts();
}
