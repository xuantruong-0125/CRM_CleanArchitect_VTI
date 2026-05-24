package org.example.crm_project.modules.customers_managerment.infrastructure.repository;

import org.example.crm_project.modules.customers_managerment.domain.entity.Feedback;
import org.example.crm_project.modules.customers_managerment.domain.repository.FeedbackRepository;
import org.example.crm_project.modules.customers_managerment.infrastructure.persistence.entity.FeedbackEntity;
import org.example.crm_project.modules.customers_managerment.infrastructure.persistence.jpa.FeedbackJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Repository Implementation: FeedbackRepositoryImpl
 */
@Repository
public class FeedbackRepositoryImpl implements FeedbackRepository {

    private final FeedbackJpaRepository jpaRepository;

    public FeedbackRepositoryImpl(FeedbackJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Feedback save(Feedback feedback) {
        FeedbackEntity entity = domainToEntity(feedback);
        FeedbackEntity saved = jpaRepository.save(entity);
        return entityToDomain(saved);
    }

    @Override
    public Optional<Feedback> findById(Long id) {
        return jpaRepository.findById(id).map(this::entityToDomain);
    }

    @Override
    public Page<Feedback> findByCustomerId(Long customerId, Pageable pageable) {
        Page<FeedbackEntity> page = jpaRepository.findByCustomerId(customerId, pageable);
        return page.map(this::entityToDomain);
    }

    @Override
    public Page<Feedback> findByStatus(String status, Pageable pageable) {
        Page<FeedbackEntity> page = jpaRepository.findByStatus(status, pageable);
        return page.map(this::entityToDomain);
    }

    @Override
    public Page<Feedback> findByPriority(String priority, Pageable pageable) {
        Page<FeedbackEntity> page = jpaRepository.findByPriority(priority, pageable);
        return page.map(this::entityToDomain);
    }

    @Override
    public void delete(Long id) {
        Optional<FeedbackEntity> entity = jpaRepository.findById(id);
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

    private Feedback entityToDomain(FeedbackEntity entity) {
        if (entity == null) return null;

        Feedback feedback = new Feedback();
        feedback.setId(entity.getId());
        feedback.setCustomerId(entity.getCustomerId());
        feedback.setSubject(entity.getSubject());
        feedback.setDescription(entity.getDescription());
        feedback.setPriority(entity.getPriority());
        feedback.setStatus(entity.getStatus());
        feedback.setAssignedTo(entity.getAssignedTo());
        feedback.setCreatedBy(entity.getCreatedBy());
        feedback.setUpdatedBy(entity.getUpdatedBy());
        feedback.setCreatedAt(entity.getCreatedAt());
        feedback.setUpdatedAt(entity.getUpdatedAt());
        feedback.setDeletedAt(entity.getDeletedAt());

        return feedback;
    }

    private FeedbackEntity domainToEntity(Feedback feedback) {
        if (feedback == null) return null;

        FeedbackEntity entity = new FeedbackEntity();
        entity.setId(feedback.getId());
        entity.setCustomerId(feedback.getCustomerId());
        entity.setSubject(feedback.getSubject());
        entity.setDescription(feedback.getDescription());
        entity.setPriority(feedback.getPriority());
        entity.setStatus(feedback.getStatus());
        entity.setAssignedTo(feedback.getAssignedTo());
        entity.setCreatedBy(feedback.getCreatedBy());
        entity.setUpdatedBy(feedback.getUpdatedBy());
        entity.setCreatedAt(feedback.getCreatedAt());
        entity.setUpdatedAt(feedback.getUpdatedAt());
        entity.setDeletedAt(feedback.getDeletedAt());

        return entity;
    }
}
