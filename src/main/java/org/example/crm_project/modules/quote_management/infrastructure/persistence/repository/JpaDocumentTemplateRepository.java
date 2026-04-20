package org.example.crm_project.modules.quote_management.infrastructure.persistence.repository;

import org.example.crm_project.modules.quote_management.infrastructure.persistence.entity.DocumentTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository cho DocumentTemplateEntity (read-only trong module báo giá).
 */
@Repository
public interface JpaDocumentTemplateRepository extends JpaRepository<DocumentTemplateEntity, Integer> {

    @Query("SELECT t FROM DocumentTemplateEntity t WHERE t.type = :type AND t.isActive = true")
    List<DocumentTemplateEntity> findByTypeAndIsActiveTrue(@Param("type") String type);

    @Query("SELECT t FROM DocumentTemplateEntity t WHERE t.isActive = true")
    List<DocumentTemplateEntity> findAllActive();
}
