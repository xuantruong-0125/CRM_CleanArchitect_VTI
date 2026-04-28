package org.example.crm_project.modules.products_managerment.infrastructure.persistence.repository;

import org.example.crm_project.modules.products_managerment.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT p FROM ProductEntity p WHERE p.isActive = true AND p.deletedAt IS NULL AND p.categoryEntity.isActive = true AND p.categoryEntity.deletedAt IS NULL")
    List<ProductEntity> findAllActive();

    @Query("SELECT p FROM ProductEntity p WHERE p.isActive = true AND p.deletedAt IS NULL AND p.categoryEntity.isActive = true AND p.categoryEntity.deletedAt IS NULL")
    Page<ProductEntity> findAllActive(Pageable pageable);

    @Query("SELECT p FROM ProductEntity p WHERE " +
           "(:keyword IS NULL OR p.name LIKE %:keyword% OR p.skuCode LIKE %:keyword% OR p.description LIKE %:keyword%) " +
           "AND (:categoryId IS NULL OR p.categoryEntity.id = :categoryId) " +
           "AND p.isActive = true AND p.deletedAt IS NULL AND p.categoryEntity.isActive = true AND p.categoryEntity.deletedAt IS NULL")
    Page<ProductEntity> search(@Param("keyword") String keyword, @Param("categoryId") Long categoryId, Pageable pageable);

    @Query("SELECT COUNT(p) FROM ProductEntity p WHERE " +
           "(:keyword IS NULL OR p.name LIKE %:keyword% OR p.skuCode LIKE %:keyword% OR p.description LIKE %:keyword%) " +
           "AND (:categoryId IS NULL OR p.categoryEntity.id = :categoryId) " +
           "AND p.isActive = true AND p.deletedAt IS NULL AND p.categoryEntity.isActive = true AND p.categoryEntity.deletedAt IS NULL")
    long countSearch(@Param("keyword") String keyword, @Param("categoryId") Long categoryId);

    @Query("SELECT p.categoryEntity.id, COUNT(p) FROM ProductEntity p WHERE p.isActive = true AND p.deletedAt IS NULL AND p.categoryEntity.isActive = true AND p.categoryEntity.deletedAt IS NULL GROUP BY p.categoryEntity.id")
    List<Object[]> countActiveProductsGroupedByCategory();

    @Query("SELECT COUNT(p) > 0 FROM ProductEntity p WHERE p.skuCode = :skuCode AND p.isActive = true AND p.deletedAt IS NULL AND p.categoryEntity.isActive = true AND p.categoryEntity.deletedAt IS NULL")
    boolean existsBySkuCodeAndIsActiveTrue(@Param("skuCode") String skuCode);

    @Query("SELECT COUNT(p) > 0 FROM ProductEntity p WHERE p.skuCode = :skuCode AND p.id != :id AND p.isActive = true AND p.deletedAt IS NULL AND p.categoryEntity.isActive = true AND p.categoryEntity.deletedAt IS NULL")
    boolean existsBySkuCodeAndIdNotAndIsActiveTrue(@Param("skuCode") String skuCode, @Param("id") Long id);
}
