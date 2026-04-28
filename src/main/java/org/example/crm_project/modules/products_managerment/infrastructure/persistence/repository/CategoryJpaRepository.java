package org.example.crm_project.modules.products_managerment.infrastructure.persistence.repository;

import org.example.crm_project.modules.products_managerment.infrastructure.persistence.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {

    @Query("SELECT c FROM CategoryEntity c WHERE c.isActive = true AND c.deletedAt IS NULL")
    List<CategoryEntity> findAllActive();

    @Query("SELECT c FROM CategoryEntity c WHERE c.isActive = true AND c.deletedAt IS NULL")
    Page<CategoryEntity> findAllActive(Pageable pageable);

    @Query("SELECT c FROM CategoryEntity c WHERE (:keyword IS NULL OR :keyword = '' OR c.name LIKE %:keyword% OR c.description LIKE %:keyword%) AND c.isActive = true AND c.deletedAt IS NULL")
    Page<CategoryEntity> search(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT COUNT(c) FROM CategoryEntity c WHERE (:keyword IS NULL OR :keyword = '' OR c.name LIKE %:keyword% OR c.description LIKE %:keyword%) AND c.isActive = true AND c.deletedAt IS NULL")
    long countSearch(@Param("keyword") String keyword);
    @Query("SELECT COUNT(c) FROM CategoryEntity c WHERE c.isActive = true AND c.deletedAt IS NULL")
    long countActive();
}
