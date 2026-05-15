package org.example.crm_project.modules.products_managerment.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.products_managerment.domain.entity.Product;
import org.example.crm_project.modules.products_managerment.domain.repository.ProductRepository;
import org.example.crm_project.modules.products_managerment.infrastructure.persistence.entity.ProductEntity;
import org.example.crm_project.modules.products_managerment.infrastructure.persistence.mapper.ProductPersistenceMapper;
import org.example.crm_project.modules.products_managerment.infrastructure.persistence.repository.ProductJpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("productsProductRepositoryImpl")
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository jpaRepository;

    @Override
    public Product save(Product product) {
        ProductEntity entity = ProductPersistenceMapper.toEntity(product);
        ProductEntity saved = jpaRepository.save(entity);
        return ProductPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jpaRepository.findById(id)
                .map(ProductPersistenceMapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return jpaRepository.findAllActive().stream()
                .map(ProductPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<Product> findAll(int page, int size) {
        return jpaRepository.findAllActive(PageRequest.of(page, size)).stream()
                .map(ProductPersistenceMapper::toDomain)
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
    public List<Product> search(String keyword, Long categoryId, Double minPrice, Double maxPrice, String status, int page, int size) {
        return jpaRepository.search(keyword, categoryId, minPrice, maxPrice, status, PageRequest.of(page, size)).stream()
                .map(ProductPersistenceMapper::toDomain)
                .toList();
    }
    
    @Override
    public long countSearch(String keyword, Long categoryId, Double minPrice, Double maxPrice, String status) {
        return jpaRepository.countSearch(keyword, categoryId, minPrice, maxPrice, status);
    }

    @Override
    public boolean existsBySkuCode(String skuCode) {
        return jpaRepository.existsBySkuCodeAndIsActiveTrue(skuCode);
    }

    @Override
    public boolean existsBySkuCodeExcludeId(String skuCode, Long id) {
        return jpaRepository.existsBySkuCodeAndIdNotAndIsActiveTrue(skuCode, id);
    }
}
