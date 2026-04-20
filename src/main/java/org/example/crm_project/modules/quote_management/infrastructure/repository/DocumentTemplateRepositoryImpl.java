package org.example.crm_project.modules.quote_management.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.quote_management.domain.constant.TemplateType;
import org.example.crm_project.modules.quote_management.domain.entity.DocumentTemplate;
import org.example.crm_project.modules.quote_management.domain.repository.DocumentTemplateRepository;
import org.example.crm_project.modules.quote_management.infrastructure.persistence.mapper.DocumentTemplateJpaMapper;
import org.example.crm_project.modules.quote_management.infrastructure.persistence.repository.JpaDocumentTemplateRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation của DocumentTemplateRepository (domain interface).
 */
@Repository
@RequiredArgsConstructor
public class DocumentTemplateRepositoryImpl implements DocumentTemplateRepository {

    private final JpaDocumentTemplateRepository jpaRepository;

    @Override
    public List<DocumentTemplate> findByTypeAndIsActiveTrue(TemplateType type) {
        return jpaRepository.findByTypeAndIsActiveTrue(type.name())
                .stream()
                .map(DocumentTemplateJpaMapper::toDomain)
                .toList();
    }

    @Override
    public List<DocumentTemplate> findAllActive() {
        return jpaRepository.findAllActive()
                .stream()
                .map(DocumentTemplateJpaMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<DocumentTemplate> findById(Integer id) {
        return jpaRepository.findById(id)
                .map(DocumentTemplateJpaMapper::toDomain);
    }
}
