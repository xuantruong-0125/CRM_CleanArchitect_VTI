package org.example.crm_project.modules.customers_managerment.infrastructure.persistence.jpa;

import org.example.crm_project.modules.customers_managerment.infrastructure.persistence.entity.AttachmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository: AttachmentJpaRepository
 */
@Repository
public interface AttachmentJpaRepository extends JpaRepository<AttachmentEntity, Long> {
    List<AttachmentEntity> findByRelatedToTypeAndRelatedToId(String relatedToType, Long relatedToId);
    Page<AttachmentEntity> findByRelatedToTypeAndRelatedToId(String relatedToType, Long relatedToId, Pageable pageable);
    void deleteByRelatedToTypeAndRelatedToId(String relatedToType, Long relatedToId);
}
