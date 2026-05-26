package org.example.crm_project.modules.quote_management.infrastructure.persistence.repository;

import org.example.crm_project.modules.products_managerment.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaProductForQuoteRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT DISTINCT p FROM ProductsProductEntity p LEFT JOIN FETCH p.prices WHERE p.isActive = true AND p.deletedAt IS NULL")
    List<ProductEntity> findAllActiveForQuoteForm();
}
