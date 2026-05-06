package org.example.crm_project.modules.task_managerment.domain.repository;

import java.util.Optional;
import org.example.crm_project.modules.task_managerment.domain.entity.Task;

public interface TaskRepository {
    Task save(Task task);
    Optional<Task> findById(Long id);
    void deleteById(Long id);
    boolean existsById(Long id);

}
