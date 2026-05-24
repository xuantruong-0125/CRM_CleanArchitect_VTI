package org.example.crm_project.modules.task_managerment.infrastructure.persistence.repository;

import java.util.List;

import org.example.crm_project.modules.task_managerment.infrastructure.persistence.entity.TaskHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTaskHistoryRepository extends JpaRepository<TaskHistoryEntity, Long>{
    List<TaskHistoryEntity> findByTaskIdOrderByCreatedAtDesc(Long taskId);

}
