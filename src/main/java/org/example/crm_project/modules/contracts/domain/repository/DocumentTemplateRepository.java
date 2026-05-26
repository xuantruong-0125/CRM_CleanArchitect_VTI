package org.example.crm_project.modules.contracts.domain.repository;

import org.example.crm_project.modules.contracts.infrastructure.persistence.entity.DocumentTemplateJpaEntity;

import java.util.List;
import java.util.Optional;

public interface DocumentTemplateRepository {
    Optional<DocumentTemplateJpaEntity> findById(Long id);
    List<DocumentTemplateJpaEntity> findAllContractTemplates();
}
