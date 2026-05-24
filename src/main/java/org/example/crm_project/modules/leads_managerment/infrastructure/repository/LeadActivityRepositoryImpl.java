package org.example.crm_project.modules.leads_managerment.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.leads_managerment.domain.entity.LeadActivityStatistics;
import org.example.crm_project.modules.leads_managerment.domain.repository.LeadActivityRepository;
import org.example.crm_project.modules.leads_managerment.infrastructure.persistence.repository.JpaLeadActivityRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class LeadActivityRepositoryImpl implements LeadActivityRepository {

    private final JpaLeadActivityRepository jpaLeadActivityRepository;

    @Override
    @Transactional(readOnly = true)
    public LeadActivityStatistics getStatisticsByLeadId(Long leadId) {
        return jpaLeadActivityRepository.getActivityStatistics(leadId);
    }
}
