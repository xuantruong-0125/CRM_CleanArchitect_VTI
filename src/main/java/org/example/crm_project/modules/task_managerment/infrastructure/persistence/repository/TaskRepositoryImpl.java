package org.example.crm_project.modules.task_managerment.infrastructure.persistence.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.example.crm_project.modules.task_managerment.domain.constant.TaskPriority;
import org.example.crm_project.modules.task_managerment.domain.constant.TaskStatus;
import org.example.crm_project.modules.task_managerment.domain.entity.Task;
import org.example.crm_project.modules.task_managerment.domain.repository.TaskRepository;
import org.example.crm_project.modules.task_managerment.infrastructure.persistence.entity.TaskJpaEntity;
import org.example.crm_project.modules.task_managerment.infrastructure.persistence.mapper.TaskJpaMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {
    
    private final JpaTaskRepository jpaRepository; 

    @Override
    public Optional<Task> findById(Long id) {
        return jpaRepository.findById(id).map(TaskJpaMapper::toDomain);
    }

    @Override
    public Page<Task> searchTasks(String subject, TaskStatus status, TaskPriority priority,LocalDateTime fromDateTime,LocalDateTime toDateTime,Long currentUserId, Long organizationId,String scope, Pageable pageable) {
        
        Page<TaskJpaEntity> jpaPage = jpaRepository.searchTasks(subject, status, priority, fromDateTime, toDateTime, currentUserId, organizationId, scope, pageable);
        
        return jpaPage.map(TaskJpaMapper::toDomain);
    }

    @Override
    public Task save(Task task) {
        TaskJpaEntity entity = TaskJpaMapper.toJpa(task);
        
        TaskJpaEntity savedEntity = jpaRepository.save(entity);
        
        return TaskJpaMapper.toDomain(savedEntity);
    }
  
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }
}
