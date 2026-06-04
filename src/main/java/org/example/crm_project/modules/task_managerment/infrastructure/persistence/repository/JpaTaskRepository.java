package org.example.crm_project.modules.task_managerment.infrastructure.persistence.repository;

import java.time.LocalDateTime;

import org.example.crm_project.modules.task_managerment.domain.constant.TaskPriority;
import org.example.crm_project.modules.task_managerment.domain.constant.TaskStatus;
import org.example.crm_project.modules.task_managerment.infrastructure.persistence.entity.TaskJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTaskRepository extends JpaRepository<TaskJpaEntity, Long> {
        @Query("SELECT t FROM TaskJpaEntity t WHERE " +
                        "(:subject IS NULL OR :subject = '' OR LOWER(t.subject) LIKE LOWER(CONCAT('%', :subject, '%'))) AND "
                        +
                        "(:status IS NULL OR t.status = :status) AND " +
                        "(:priority IS NULL OR t.priority = :priority) AND " +
                        " (:fromDateTime IS NULL OR t.startDate >= :fromDateTime) AND " +
                        " (:toDateTime IS NULL OR t.dueDate <= :toDateTime) AND " +
                        " ( :scope = 'ALL' OR " +
                        "   (:scope = 'OWN' AND (t.assignedTo = :currentUserId OR t.assignedBy = :currentUserId)) OR " +
                        "   (:scope = 'BRANCH' AND t.organizationId = :organizationId)" +
                        ")")
        Page<TaskJpaEntity> searchTasks(
                        @Param("subject") String subject,
                        @Param("status") TaskStatus status,
                        @Param("priority") TaskPriority priority,
                        @Param("fromDateTime") LocalDateTime fromDateTime,
                        @Param("toDateTime") LocalDateTime toDateTime,
                        @Param("currentUserId") Long currentUserId,
                        @Param("organizationId") Long organizationId,
                        @Param("scope") String scope,
                        Pageable pageable);

}
