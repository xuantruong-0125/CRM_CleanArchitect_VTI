package org.example.crm_project.modules.customers.infrastructure.repository;

import org.example.crm_project.modules.customers.domain.entity.Activity;
import org.example.crm_project.modules.customers.domain.repository.ActivityRepository;
import org.example.crm_project.modules.customers.infrastructure.persistence.entity.ActivityEntity;
import org.example.crm_project.modules.customers.infrastructure.persistence.jpa.ActivityJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repository Implementation: ActivityRepositoryImpl
 */
@Repository
public class ActivityRepositoryImpl implements ActivityRepository {

    private final ActivityJpaRepository jpaRepository;

    public ActivityRepositoryImpl(ActivityJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Activity save(Activity activity) {
        ActivityEntity entity = domainToEntity(activity);
        ActivityEntity saved = jpaRepository.save(entity);
        return entityToDomain(saved);
    }

    @Override
    public Optional<Activity> findById(Long id) {
        return jpaRepository.findById(id).map(this::entityToDomain);
    }

    @Override
    public List<Activity> findByRelatedToTypeAndRelatedToId(String relatedToType, Long relatedToId) {
        return jpaRepository.findByRelatedToTypeAndRelatedToId(relatedToType, relatedToId).stream()
                .map(this::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Activity> findByRelatedToTypeAndRelatedToId(String relatedToType, Long relatedToId, Pageable pageable) {
        Page<ActivityEntity> page = jpaRepository.findByRelatedToTypeAndRelatedToId(relatedToType, relatedToId, pageable);
        return page.map(this::entityToDomain);
    }

    @Override
    public Page<Activity> findByPerformedBy(Long userId, Pageable pageable) {
        Page<ActivityEntity> page = jpaRepository.findByPerformedBy(userId, pageable);
        return page.map(this::entityToDomain);
    }

    @Override
    public Page<Activity> findByActivityType(String activityType, Pageable pageable) {
        Page<ActivityEntity> page = jpaRepository.findByActivityType(activityType, pageable);
        return page.map(this::entityToDomain);
    }

    @Override
    public List<Activity> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return jpaRepository.findByDateRange(startDate, endDate).stream()
                .map(this::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Optional<ActivityEntity> entity = jpaRepository.findById(id);
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

    @Override
    public long countByRelatedToTypeAndRelatedToId(String relatedToType, Long relatedToId) {
        return jpaRepository.countByRelatedToTypeAndRelatedToId(relatedToType, relatedToId);
    }

    // Helper methods
    private Activity entityToDomain(ActivityEntity entity) {
        if (entity == null) return null;

        Activity activity = new Activity();
        activity.setId(entity.getId());
        activity.setActivityType(entity.getActivityType() != null ? 
                org.example.crm_project.modules.customers.domain.constant.ActivityType.fromCode(entity.getActivityType()) : null);
        activity.setSubject(entity.getSubject());
        activity.setDescription(entity.getDescription());
        activity.setStartDate(entity.getStartDate());
        activity.setEndDate(entity.getEndDate());
        activity.setCompletedAt(entity.getCompletedAt());
        activity.setOutcome(entity.getOutcome());
        activity.setRelatedToType(entity.getRelatedToType());
        activity.setRelatedToId(entity.getRelatedToId());
        activity.setPerformedBy(entity.getPerformedBy());
        activity.setCreatedBy(entity.getCreatedBy());
        activity.setUpdatedBy(entity.getUpdatedBy());
        activity.setCreatedAt(entity.getCreatedAt());
        activity.setUpdatedAt(entity.getUpdatedAt());
        //activity.setStatus(entity.getStatus());
        activity.setIsImportant(entity.getIsImportant());
        activity.setDeletedAt(entity.getDeletedAt());

        return activity;
    }

    private ActivityEntity domainToEntity(Activity activity) {
        if (activity == null) return null;

        ActivityEntity entity = new ActivityEntity();
        entity.setId(activity.getId());
        //entity.setActivityType(activity.getActivityType() != null ? activity.getActivityType().getCode() : null);
        entity.setSubject(activity.getSubject());
        entity.setDescription(activity.getDescription());
        entity.setStartDate(activity.getStartDate());
        entity.setEndDate(activity.getEndDate());
        entity.setCompletedAt(activity.getCompletedAt());
        entity.setOutcome(activity.getOutcome());
        entity.setRelatedToType(activity.getRelatedToType());
        entity.setRelatedToId(activity.getRelatedToId());
        entity.setPerformedBy(activity.getPerformedBy());
        entity.setCreatedBy(activity.getCreatedBy());
        entity.setUpdatedBy(activity.getUpdatedBy());
        entity.setCreatedAt(activity.getCreatedAt());
        entity.setUpdatedAt(activity.getUpdatedAt());
        //entity.setStatus(activity.getStatus());
        entity.setIsImportant(activity.getIsImportant());
        entity.setDeletedAt(activity.getDeletedAt());

        return entity;
    }
}
