package org.example.crm_project.modules.leads.infrastructure.persistence.repository;

import org.example.crm_project.modules.leads.infrastructure.persistence.entity.LeadActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaLeadActivityRepository extends JpaRepository<LeadActivityEntity, Long> {

    List<LeadActivityEntity> findAllByRelatedToTypeAndRelatedToIdAndDeletedAtIsNullOrderByCreatedAtDesc(String relatedToType, Long relatedToId);

  java.util.Optional<LeadActivityEntity> findByIdAndRelatedToTypeAndRelatedToIdAndDeletedAtIsNull(Long id, String relatedToType, Long relatedToId);

    @Query("""
            select
                sum(case when a.activityType = 2 then 1 else 0 end),
                sum(case when a.activityType = 3 then 1 else 0 end),
                sum(case when a.activityType = 1 then 1 else 0 end),
                count(a)
            from LeadActivityEntity a
            where a.relatedToType = 'LEAD'
              and a.relatedToId = :leadId
              and a.deletedAt is null
            """)
    Object[] getActivityStatistics(@Param("leadId") Long leadId);
}