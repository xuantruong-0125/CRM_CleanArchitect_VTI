package org.example.crm_project.modules.customers.domain.repository;

import org.example.crm_project.modules.customers.domain.entity.Attachment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Repository Interface: AttachmentRepository
 */
public interface AttachmentRepository {
    Attachment save(Attachment attachment);
    Optional<Attachment> findById(Long id);
    List<Attachment> findByRelatedToTypeAndRelatedToId(String relatedToType, Long relatedToId);
    Page<Attachment> findByRelatedToTypeAndRelatedToId(String relatedToType, Long relatedToId, Pageable pageable);
    void delete(Long id);
    void deleteByRelatedToTypeAndRelatedToId(String relatedToType, Long relatedToId);
    boolean existsById(Long id);
    long count();
}
