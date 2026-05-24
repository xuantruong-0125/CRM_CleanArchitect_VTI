package org.example.crm_project.modules.leads_managerment.infrastructure.persistence.repository;

import org.example.crm_project.modules.activity_management.infrastructure.persistence.entity.ActivityJpaEntity;
import org.example.crm_project.modules.leads_managerment.domain.entity.LeadActivityStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaLeadActivityRepository extends JpaRepository<ActivityJpaEntity, Long> {

    @Query("""
      select new org.example.crm_project.modules.leads_managerment.domain.entity.LeadActivityStatistics(
        coalesce(sum(case when a.activityType = 1 then 1 else 0 end), 0),
        coalesce(sum(case when a.activityType = 2 then 1 else 0 end), 0),
        coalesce(sum(case when a.activityType = 3 then 1 else 0 end), 0),
        count(a)
      )
            from ActivityJpaEntity a
            where a.relatedToType = 'LEAD'
              and a.relatedToId = :leadId
              and a.deletedAt is null
            """)
    LeadActivityStatistics getActivityStatistics(@Param("leadId") Long leadId);
}
