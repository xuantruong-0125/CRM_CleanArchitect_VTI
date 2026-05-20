package org.example.crm_project.modules.products_managerment.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.products_managerment.domain.entity.Category;
import org.example.crm_project.modules.products_managerment.domain.repository.CategoryRepository;
import org.example.crm_project.modules.products_managerment.infrastructure.persistence.entity.CategoryEntity;
import org.example.crm_project.modules.products_managerment.infrastructure.persistence.mapper.CategoryPersistenceMapper;
import org.example.crm_project.modules.products_managerment.infrastructure.persistence.repository.CategoryJpaRepository;
import org.example.crm_project.modules.products_managerment.infrastructure.persistence.repository.ProductJpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository jpaRepository;
    private final ProductJpaRepository productJpaRepository;

    @Override
    public Category save(Category category) {
        CategoryEntity entity = CategoryPersistenceMapper.toEntity(category);
        CategoryEntity saved = jpaRepository.save(entity);
        return CategoryPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return jpaRepository.findById(id)
                .map(CategoryPersistenceMapper::toDomain);
    }

    @Override
    public List<Category> findAll() {
        return jpaRepository.findAllActive().stream()
                .map(CategoryPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<Category> findAll(int page, int size) {
        return jpaRepository.findAllActive(PageRequest.of(page, size)).stream()
                .map(CategoryPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public List<Category> search(String keyword, int page, int size) {
        return jpaRepository.search(keyword, PageRequest.of(page, size)).stream()
                .map(CategoryPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public long countSearch(String keyword) {
        return jpaRepository.countSearch(keyword);
    }

    @Override
    public long countActive() {
        return jpaRepository.countActive();
    }

    @Override
    public Map<Long, Integer> getProductCounts() {
        return productJpaRepository.countActiveProductsGroupedByCategory().stream()
                .collect(Collectors.toMap(
                        row -> (Long) row[0],
                        row -> ((Long) row[1]).intValue()
                ));
    }
}
