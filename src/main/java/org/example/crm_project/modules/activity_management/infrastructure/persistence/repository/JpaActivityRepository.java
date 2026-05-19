package org.example.crm_project.modules.activity_management.infrastructure.persistence.repository;

import org.example.crm_project.modules.activity_management.infrastructure.persistence.entity.ActivityJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JpaActivityRepository extends JpaRepository<ActivityJpaEntity, Long> {

        // Spring Data JPA sẽ tự động tạo query tìm kiếm theo Target và sắp xếp mới nhất
        // lên đầu
        List<ActivityJpaEntity> findByRelatedToTypeAndRelatedToIdOrderByCreatedAtDesc(String type, Long id);

        @Query("SELECT a FROM ActivityJpaEntity a WHERE " +
                        "(:scope = 'ALL' OR " +
                        " (:scope = 'OWN' AND a.performedBy = :currentUserId) OR " +
                        " (:scope = 'BRANCH' AND a.organizationId = :organizationId))")
        Page<ActivityJpaEntity> findAllWithScope(
                        @Param("currentUserId") Long currentUserId,
                        @Param("organizationId") Long organizationId,
                        @Param("scope") String scope,
                        Pageable pageable);

        @Query("SELECT a FROM ActivityJpaEntity a " +
                        "WHERE (:search IS NULL OR LOWER(a.subject) LIKE LOWER(CONCAT('%', :search, '%')) " +
                        "OR LOWER(a.description) LIKE LOWER(CONCAT('%', :search, '%'))) " +
                        "AND (:status IS NULL OR a.status = :status) " +
                        "AND (:type IS NULL OR a.activityType = :type) " +
                        "AND (:performedBy IS NULL OR a.performedBy = :performedBy) " +
                        "AND (:relatedToId IS NULL OR a.relatedToId = :relatedToId) " +
                        "AND (:relatedToType IS NULL OR a.relatedToType = :relatedToType) " +
                        "AND (:fromDate IS NULL OR a.startDate >= :fromDate) " +
                        "AND (:toDate IS NULL OR a.startDate <= :toDate)" +
                        "AND (:important IS NULL OR a.isImportant = :important)" +
                        "AND (" +
                        "   :scope = 'ALL' OR " +
                        "   (:scope = 'OWN' AND a.performedBy = :currentUserId) OR " +
                        "   (:scope = 'BRANCH' AND a.organizationId = :organizationId)" +
                        ")")
        Page<ActivityJpaEntity> searchActivities(
                        @Param("search") String search,
                        @Param("status") Integer status,
                        @Param("type") Integer type,
                        @Param("performedBy") Long performedBy,
                        @Param("relatedToId") Long relatedToId,
                        @Param("relatedToType") String relatedToType,
                        @Param("fromDate") LocalDateTime fromDate,
                        @Param("toDate") LocalDateTime toDate,
                        @Param("important") Boolean important,
                        @Param("currentUserId") Long currentUserId,
                        @Param("organizationId") Long organizationId,
                        @Param("scope") String scope,
                        Pageable pageable);
}