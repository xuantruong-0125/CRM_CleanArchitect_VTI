package org.example.crm_project.modules.products_managerment.infrastructure.persistence.repository;

import org.example.crm_project.modules.products_managerment.infrastructure.persistence.entity.PriceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {

    @Query("SELECT p FROM PriceEntity p WHERE p.productEntity.id = :productId AND p.isActive = true AND p.deletedAt IS NULL AND p.productEntity.isActive = true AND p.productEntity.deletedAt IS NULL AND p.productEntity.categoryEntity.isActive = true AND p.productEntity.categoryEntity.deletedAt IS NULL")
    List<PriceEntity> findByProductId(@Param("productId") Long productId);

    @Query("SELECT p FROM PriceEntity p WHERE p.isActive = true AND p.deletedAt IS NULL AND p.productEntity.isActive = true AND p.productEntity.deletedAt IS NULL AND p.productEntity.categoryEntity.isActive = true AND p.productEntity.categoryEntity.deletedAt IS NULL")
    List<PriceEntity> findAllActive();

    @Query("SELECT p FROM PriceEntity p WHERE p.isActive = true AND p.deletedAt IS NULL AND p.productEntity.isActive = true AND p.productEntity.deletedAt IS NULL AND p.productEntity.categoryEntity.isActive = true AND p.productEntity.categoryEntity.deletedAt IS NULL")
    Page<PriceEntity> findAllActive(Pageable pageable);

    @Query("SELECT p FROM PriceEntity p WHERE " +
           "(:keyword IS NULL OR LOWER(p.productEntity.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.productEntity.skuCode) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "AND (:productId IS NULL OR p.productEntity.id = :productId) " +
           "AND p.isActive = true AND p.deletedAt IS NULL AND p.productEntity.isActive = true AND p.productEntity.deletedAt IS NULL AND p.productEntity.categoryEntity.isActive = true AND p.productEntity.categoryEntity.deletedAt IS NULL")
    Page<PriceEntity> search(@Param("keyword") String keyword, @Param("productId") Long productId, Pageable pageable);

    @Query("SELECT COUNT(p) FROM PriceEntity p WHERE " +
           "(:keyword IS NULL OR LOWER(p.productEntity.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.productEntity.skuCode) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "AND (:productId IS NULL OR p.productEntity.id = :productId) " +
           "AND p.isActive = true AND p.deletedAt IS NULL AND p.productEntity.isActive = true AND p.productEntity.deletedAt IS NULL AND p.productEntity.categoryEntity.isActive = true AND p.productEntity.categoryEntity.deletedAt IS NULL")
    long countSearch(@Param("keyword") String keyword, @Param("productId") Long productId);
}
