package org.example.crm_project.modules.leads.infrastructure.persistence.repository;

import org.example.crm_project.modules.leads.infrastructure.persistence.entity.LeadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface JpaLeadRepository extends JpaRepository<LeadEntity, Long>, JpaSpecificationExecutor<LeadEntity> {

    Optional<LeadEntity> findByIdAndDeletedAtIsNull(Long id);

    List<LeadEntity> findAllByDeletedAtIsNull();

    boolean existsByIdAndDeletedAtIsNull(Long id);
}