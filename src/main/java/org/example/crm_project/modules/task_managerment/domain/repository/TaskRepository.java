package org.example.crm_project.modules.task_managerment.domain.repository;

import java.util.Optional;

import org.example.crm_project.modules.task_managerment.domain.constant.TaskPriority;
import org.example.crm_project.modules.task_managerment.domain.constant.TaskStatus;
import org.example.crm_project.modules.task_managerment.domain.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskRepository {
    Task save(Task task);
    Optional<Task> findById(Long id);
    void deleteById(Long id);
    boolean existsById(Long id);
    Page<Task> searchTasks(String subject, TaskStatus status, TaskPriority priority, Pageable pageable);

}
