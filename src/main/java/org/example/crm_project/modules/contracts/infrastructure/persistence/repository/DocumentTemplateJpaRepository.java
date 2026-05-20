package org.example.crm_project.modules.contracts.infrastructure.persistence.repository;

import org.example.crm_project.modules.contracts.infrastructure.persistence.entity.DocumentTemplateJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentTemplateJpaRepository extends JpaRepository<DocumentTemplateJpaEntity, Long> {

    List<DocumentTemplateJpaEntity> findByTypeAndIsActiveTrue(String type);
}
