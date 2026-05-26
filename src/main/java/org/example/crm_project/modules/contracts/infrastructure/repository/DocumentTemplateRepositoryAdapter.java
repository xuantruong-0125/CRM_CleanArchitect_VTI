package org.example.crm_project.modules.contracts.infrastructure.repository;

import org.example.crm_project.modules.contracts.domain.repository.DocumentTemplateRepository;
import org.example.crm_project.modules.contracts.infrastructure.persistence.entity.DocumentTemplateJpaEntity;
import org.example.crm_project.modules.contracts.infrastructure.persistence.repository.DocumentTemplateJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DocumentTemplateRepositoryAdapter implements DocumentTemplateRepository {

    private final DocumentTemplateJpaRepository jpaRepository;

    @Override
    public Optional<DocumentTemplateJpaEntity> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<DocumentTemplateJpaEntity> findAllContractTemplates() {
        return jpaRepository.findByTypeAndIsActiveTrue("CONTRACT");
    }
}
