package org.example.crm_project.modules.customers.infrastructure.persistence.jpa;

import org.example.crm_project.modules.customers.infrastructure.persistence.entity.ActivityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * JPA Repository: ActivityJpaRepository
 */
@Repository
public interface ActivityJpaRepository extends JpaRepository<ActivityEntity, Long> {

    /**
     * Tìm hoạt động theo loại và ID
     */
    List<ActivityEntity> findByRelatedToTypeAndRelatedToId(String relatedToType, Long relatedToId);

    /**
     * Tìm hoạt động phân trang
     */
    Page<ActivityEntity> findByRelatedToTypeAndRelatedToId(String relatedToType, Long relatedToId, Pageable pageable);

    /**
     * Tìm hoạt động của người dùng
     */
    @Query("SELECT a FROM ActivityEntity a WHERE a.performedBy = :performedBy AND a.deletedAt IS NULL")
    Page<ActivityEntity> findByPerformedBy(@Param("performedBy") Long performedBy, Pageable pageable);

    /**
     * Tìm hoạt động trong khoảng thời gian
     */
    @Query("SELECT a FROM ActivityEntity a WHERE DATE(a.startDate) BETWEEN :startDate AND :endDate AND a.deletedAt IS NULL")
    List<ActivityEntity> findByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * Đếm hoạt động của khách hàng
     */
    @Query("SELECT COUNT(a) FROM ActivityEntity a WHERE a.relatedToType = :relatedToType AND a.relatedToId = :relatedToId AND a.deletedAt IS NULL")
    long countByRelatedToTypeAndRelatedToId(@Param("relatedToType") String relatedToType, @Param("relatedToId") Long relatedToId);

    Page<ActivityEntity> findByActivityType(String activityType, Pageable pageable);
}
