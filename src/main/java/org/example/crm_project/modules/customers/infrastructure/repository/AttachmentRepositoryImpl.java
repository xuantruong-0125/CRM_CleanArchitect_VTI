package org.example.crm_project.modules.customers.infrastructure.repository;

import org.example.crm_project.modules.customers.domain.entity.Attachment;
import org.example.crm_project.modules.customers.domain.repository.AttachmentRepository;
import org.example.crm_project.modules.customers.infrastructure.persistence.entity.AttachmentEntity;
import org.example.crm_project.modules.customers.infrastructure.persistence.jpa.AttachmentJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repository Implementation: AttachmentRepositoryImpl
 */
@Repository
public class AttachmentRepositoryImpl implements AttachmentRepository {

    private final AttachmentJpaRepository jpaRepository;

    public AttachmentRepositoryImpl(AttachmentJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Attachment save(Attachment attachment) {
        AttachmentEntity entity = domainToEntity(attachment);
        AttachmentEntity saved = jpaRepository.save(entity);
        return entityToDomain(saved);
    }

    @Override
    public Optional<Attachment> findById(Long id) {
        return jpaRepository.findById(id).map(this::entityToDomain);
    }

    @Override
    public List<Attachment> findByRelatedToTypeAndRelatedToId(String relatedToType, Long relatedToId) {
        return jpaRepository.findByRelatedToTypeAndRelatedToId(relatedToType, relatedToId).stream()
                .map(this::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Attachment> findByRelatedToTypeAndRelatedToId(String relatedToType, Long relatedToId, Pageable pageable) {
        Page<AttachmentEntity> page = jpaRepository.findByRelatedToTypeAndRelatedToId(relatedToType, relatedToId, pageable);
        return page.map(this::entityToDomain);
    }

    @Override
    public void deleteByRelatedToTypeAndRelatedToId(String relatedToType, Long relatedToId) {
        List<AttachmentEntity> attachments = jpaRepository.findByRelatedToTypeAndRelatedToId(relatedToType, relatedToId);
        attachments.forEach(a -> {
            a.setDeletedAt(LocalDateTime.now());
            jpaRepository.save(a);
        });
    }

    @Override
    public void delete(Long id) {
        Optional<AttachmentEntity> entity = jpaRepository.findById(id);
        entity.ifPresent(e -> {
            e.setDeletedAt(LocalDateTime.now());
            jpaRepository.save(e);
        });
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }

    private Attachment entityToDomain(AttachmentEntity entity) {
        if (entity == null) return null;

        Attachment attachment = new Attachment();
        attachment.setId(entity.getId());
        attachment.setFileName(entity.getFileName());
        attachment.setFileType(entity.getFileType());
        attachment.setFileSize(entity.getFileSize());
        attachment.setFilePath(entity.getFilePath());
        attachment.setRelatedToType(entity.getRelatedToType());
        attachment.setRelatedToId(entity.getRelatedToId());
        attachment.setUploadedBy(entity.getUploadedBy());
        attachment.setCreatedAt(entity.getCreatedAt());
        attachment.setUpdatedAt(entity.getUpdatedAt());
        attachment.setDeletedAt(entity.getDeletedAt());

        return attachment;
    }

    private AttachmentEntity domainToEntity(Attachment attachment) {
        if (attachment == null) return null;

        AttachmentEntity entity = new AttachmentEntity();
        entity.setId(attachment.getId());
        entity.setFileName(attachment.getFileName());
        entity.setFileType(attachment.getFileType());
        entity.setFileSize(attachment.getFileSize());
        entity.setFilePath(attachment.getFilePath());
        entity.setRelatedToType(attachment.getRelatedToType());
        entity.setRelatedToId(attachment.getRelatedToId());
        entity.setUploadedBy(attachment.getUploadedBy());
        entity.setCreatedAt(attachment.getCreatedAt());
        entity.setUpdatedAt(attachment.getUpdatedAt());
        entity.setDeletedAt(attachment.getDeletedAt());

        return entity;
    }
}
