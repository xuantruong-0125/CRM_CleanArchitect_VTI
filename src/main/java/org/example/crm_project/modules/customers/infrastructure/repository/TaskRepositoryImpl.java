package org.example.crm_project.modules.customers.infrastructure.repository;

import org.example.crm_project.modules.customers.domain.entity.Task;
import org.example.crm_project.modules.customers.domain.repository.TaskRepository;
import org.example.crm_project.modules.customers.infrastructure.persistence.entity.TaskEntity;
import org.example.crm_project.modules.customers.infrastructure.persistence.jpa.TaskJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repository Implementation: TaskRepositoryImpl
 */
@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private final TaskJpaRepository jpaRepository;

    public TaskRepositoryImpl(TaskJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Task save(Task task) {
        TaskEntity entity = domainToEntity(task);
        TaskEntity saved = jpaRepository.save(entity);
        return entityToDomain(saved);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return jpaRepository.findById(id).map(this::entityToDomain);
    }

    @Override
    public Page<Task> findByAssignedTo(Long userId, Pageable pageable) {
        Page<TaskEntity> page = jpaRepository.findByAssignedTo(userId, pageable);
        return page.map(this::entityToDomain);
    }

    @Override
    public Page<Task> findByCustomerId(Long customerId, Pageable pageable) {
        Page<TaskEntity> page = jpaRepository.findByCustomerId(customerId, pageable);
        return page.map(this::entityToDomain);
    }

    @Override
    public Page<Task> findByStatus(String status, Pageable pageable) {
        Page<TaskEntity> page = jpaRepository.findByStatus(status, pageable);
        return page.map(this::entityToDomain);
    }

    @Override
    public Page<Task> findByPriority(String priority, Pageable pageable) {
        Page<TaskEntity> page = jpaRepository.findByPriority(priority, pageable);
        return page.map(this::entityToDomain);
    }

    @Override
    public List<Task> findByRelatedToTypeAndRelatedToId(String relatedToType, Long relatedToId) {
        return jpaRepository.findByRelatedToTypeAndRelatedToId(relatedToType, relatedToId).stream()
                .map(this::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findOverdueTasks(LocalDate currentDate) {
        return jpaRepository.findOverdueTasks(currentDate.atStartOfDay()).stream()
                .map(this::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findUpcomingTasks(LocalDate startDate, LocalDate endDate) {
        return jpaRepository.findUpcomingTasks(startDate.atStartOfDay(), endDate.atTime(23, 59, 59)).stream()
                .map(this::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Optional<TaskEntity> entity = jpaRepository.findById(id);
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

    // Helper methods
    private Task entityToDomain(TaskEntity entity) {
        if (entity == null) return null;

        Task task = new Task();
        task.setId(entity.getId());
        task.setSubject(entity.getSubject());
        task.setDescription(entity.getDescription());
        task.setStartDate(entity.getStartDate() != null ? entity.getStartDate().toLocalDate() : null);
        task.setDueDate(entity.getDueDate() != null ? entity.getDueDate().toLocalDate() : null);
        task.setCompletedAt(entity.getCompletedAt());
        task.setStatus(entity.getStatus() != null ? 
                org.example.crm_project.modules.customers.domain.constant.TaskStatus.valueOf(entity.getStatus()) : null);
        task.setPriority(entity.getPriority() != null ? 
                org.example.crm_project.modules.customers.domain.constant.TaskPriority.valueOf(entity.getPriority()) : null);
        task.setProgressPercent(entity.getProgressPercent());
        task.setRelatedToType(entity.getRelatedToType());
        task.setRelatedToId(entity.getRelatedToId());
        task.setAssignedTo(entity.getAssignedTo());
        task.setAssignedBy(entity.getAssignedBy());
        task.setCreatedBy(entity.getCreatedBy());
        task.setUpdatedBy(entity.getUpdatedBy());
        task.setCreatedAt(entity.getCreatedAt());
        task.setUpdatedAt(entity.getUpdatedAt());
        task.setDeletedAt(entity.getDeletedAt());
        task.setContactId(entity.getContactId());

        return task;
    }

    private TaskEntity domainToEntity(Task task) {
        if (task == null) return null;

        TaskEntity entity = new TaskEntity();
        entity.setId(task.getId());
        entity.setSubject(task.getSubject());
        entity.setDescription(task.getDescription());
        entity.setStartDate(task.getStartDate() != null ? task.getStartDate().atStartOfDay() : null);
        entity.setDueDate(task.getDueDate() != null ? task.getDueDate().atStartOfDay() : null);
        entity.setCompletedAt(task.getCompletedAt());
        entity.setStatus(task.getStatus() != null ? task.getStatus().getCode() : null);
        entity.setPriority(task.getPriority() != null ? task.getPriority().getCode() : null);
        entity.setProgressPercent(task.getProgressPercent());
        entity.setRelatedToType(task.getRelatedToType());
        entity.setRelatedToId(task.getRelatedToId());
        entity.setAssignedTo(task.getAssignedTo());
        entity.setAssignedBy(task.getAssignedBy());
        entity.setCreatedBy(task.getCreatedBy());
        entity.setUpdatedBy(task.getUpdatedBy());
        entity.setCreatedAt(task.getCreatedAt());
        entity.setUpdatedAt(task.getUpdatedAt());
        entity.setDeletedAt(task.getDeletedAt());
        entity.setContactId(task.getContactId());

        return entity;
    }
}
