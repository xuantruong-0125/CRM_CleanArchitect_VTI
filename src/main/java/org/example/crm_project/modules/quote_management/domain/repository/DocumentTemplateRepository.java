package org.example.crm_project.modules.quote_management.domain.repository;

import org.example.crm_project.modules.quote_management.domain.entity.DocumentTemplate;
import org.example.crm_project.modules.quote_management.domain.constant.TemplateType;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository interface cho DocumentTemplate (read-only trong module báo giá).
 */
public interface DocumentTemplateRepository {

    List<DocumentTemplate> findByTypeAndIsActiveTrue(TemplateType type);

    List<DocumentTemplate> findAllActive();

    Optional<DocumentTemplate> findById(Integer id);
}
