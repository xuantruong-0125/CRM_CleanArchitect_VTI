package org.example.crm_project.modules.customers.infrastructure.persistence.jpa;

import org.example.crm_project.modules.customers.infrastructure.persistence.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * JPA Repository: TaskJpaRepository
 */
@Repository
public interface TaskJpaRepository extends JpaRepository<TaskEntity, Long> {

    /**
     * Tìm công việc được gán cho người dùng
     */
    @Query("SELECT t FROM TaskEntity t WHERE t.assignedTo = :assignedTo AND t.deletedAt IS NULL")
    Page<TaskEntity> findByAssignedTo(@Param("assignedTo") Long assignedTo, Pageable pageable);

    /**
     * Tìm công việc theo trạng thái
     */
    Page<TaskEntity> findByStatus(String status, Pageable pageable);

    /**
     * Tìm công việc theo độ ưu tiên
     */
    Page<TaskEntity> findByPriority(String priority, Pageable pageable);

    /**
     * Tìm công việc theo loại và ID
     */
    List<TaskEntity> findByRelatedToTypeAndRelatedToId(String relatedToType, Long relatedToId);

    /**
     * Tìm công việc quá hạn
     */
    @Query("SELECT t FROM TaskEntity t WHERE t.dueDate < :currentDate AND t.status != 'COMPLETED' AND t.deletedAt IS NULL")
    List<TaskEntity> findOverdueTasks(@Param("currentDate") LocalDateTime currentDate);

    /**
     * Tìm công việc sắp đến hạn
     */
    @Query("SELECT t FROM TaskEntity t WHERE t.dueDate BETWEEN :startDate AND :endDate AND t.status != 'COMPLETED' AND t.deletedAt IS NULL")
    List<TaskEntity> findUpcomingTasks(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT t FROM TaskEntity t WHERE t.relatedToType = 'CUSTOMER' AND t.relatedToId = :customerId AND t.deletedAt IS NULL")
    Page<TaskEntity> findByCustomerId(@Param("customerId") Long customerId, Pageable pageable);
}
