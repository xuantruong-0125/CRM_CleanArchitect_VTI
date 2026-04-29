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

    @Query("SELECT p FROM ProductEntity p " +
           "LEFT JOIN p.prices pr ON (pr.isActive = true AND pr.deletedAt IS NULL) " +
           "WHERE (:keyword IS NULL OR :keyword = '' OR p.name LIKE CONCAT('%', :keyword, '%') OR p.skuCode LIKE CONCAT('%', :keyword, '%') OR p.description LIKE CONCAT('%', :keyword, '%')) " +
           "AND (:categoryId IS NULL OR p.categoryEntity.id = :categoryId) " +
           "AND (:minPrice IS NULL OR pr.finalPrice >= :minPrice) " +
           "AND (:maxPrice IS NULL OR pr.finalPrice <= :maxPrice) " +
           "AND (:status IS NULL OR :status = '' OR (:status = 'Active' AND p.isActive = true) OR (:status = 'Inactive' AND p.isActive = false)) " +
           "AND p.isActive = true AND p.deletedAt IS NULL AND p.categoryEntity.isActive = true AND p.categoryEntity.deletedAt IS NULL")
    Page<ProductEntity> search(
            @Param("keyword") String keyword, 
            @Param("categoryId") Long categoryId, 
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("status") String status,
            Pageable pageable);

    @Query("SELECT COUNT(DISTINCT p.id) FROM ProductEntity p " +
           "LEFT JOIN p.prices pr ON (pr.isActive = true AND pr.deletedAt IS NULL) " +
           "WHERE (:keyword IS NULL OR :keyword = '' OR p.name LIKE CONCAT('%', :keyword, '%') OR p.skuCode LIKE CONCAT('%', :keyword, '%') OR p.description LIKE CONCAT('%', :keyword, '%')) " +
           "AND (:categoryId IS NULL OR p.categoryEntity.id = :categoryId) " +
           "AND (:minPrice IS NULL OR pr.finalPrice >= :minPrice) " +
           "AND (:maxPrice IS NULL OR pr.finalPrice <= :maxPrice) " +
           "AND (:status IS NULL OR :status = '' OR (:status = 'Active' AND p.isActive = true) OR (:status = 'Inactive' AND p.isActive = false)) " +
           "AND p.isActive = true AND p.deletedAt IS NULL AND p.categoryEntity.isActive = true AND p.categoryEntity.deletedAt IS NULL")
    long countSearch(
            @Param("keyword") String keyword, 
            @Param("categoryId") Long categoryId,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("status") String status);

    @Query("SELECT p.categoryEntity.id, COUNT(p) FROM ProductEntity p WHERE p.isActive = true AND p.deletedAt IS NULL AND p.categoryEntity.isActive = true AND p.categoryEntity.deletedAt IS NULL GROUP BY p.categoryEntity.id")
    List<Object[]> countActiveProductsGroupedByCategory();

    @Query("SELECT COUNT(p) > 0 FROM ProductEntity p WHERE p.skuCode = :skuCode AND p.isActive = true AND p.deletedAt IS NULL AND p.categoryEntity.isActive = true AND p.categoryEntity.deletedAt IS NULL")
    boolean existsBySkuCodeAndIsActiveTrue(@Param("skuCode") String skuCode);

    @Query("SELECT COUNT(p) > 0 FROM ProductEntity p WHERE p.skuCode = :skuCode AND p.id != :id AND p.isActive = true AND p.deletedAt IS NULL AND p.categoryEntity.isActive = true AND p.categoryEntity.deletedAt IS NULL")
    boolean existsBySkuCodeAndIdNotAndIsActiveTrue(@Param("skuCode") String skuCode, @Param("id") Long id);
}
