package org.example.crm_project.modules.leads_managerment.infrastructure.persistence.repository;

import org.example.crm_project.modules.task_managerment.infrastructure.persistence.entity.TaskJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface JpaLeadTaskRepository extends JpaRepository<TaskJpaEntity, Long> {
    @Query("SELECT t FROM TaskJpaEntity t WHERE t.relatedToType = 'LEAD' AND t.relatedToId = :leadId")
    List<TaskJpaEntity> findByLeadId(@Param("leadId") Long leadId);
}