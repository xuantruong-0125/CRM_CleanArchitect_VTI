package org.example.crm_project.modules.leads.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.leads.domain.entity.LeadActivity;
import org.example.crm_project.modules.leads.domain.entity.LeadActivityStatistics;
import org.example.crm_project.modules.leads.domain.exception.LeadNotFoundException;
import org.example.crm_project.modules.leads.domain.repository.LeadActivityRepository;
import org.example.crm_project.modules.leads.infrastructure.persistence.mapper.LeadActivityJpaMapper;
import org.example.crm_project.modules.leads.infrastructure.persistence.repository.JpaLeadActivityRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LeadActivityRepositoryImpl implements LeadActivityRepository {

    private final JpaLeadActivityRepository jpaLeadActivityRepository;

    @Override
    @Transactional
    public LeadActivity save(LeadActivity leadActivity) {
        return LeadActivityJpaMapper.toDomain(
                jpaLeadActivityRepository.save(LeadActivityJpaMapper.toEntity(leadActivity))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public LeadActivity findByIdAndLeadId(Long activityId, Long leadId) {
        return jpaLeadActivityRepository
                .findByIdAndRelatedToTypeAndRelatedToIdAndDeletedAtIsNull(activityId, "LEAD", leadId)
                .map(LeadActivityJpaMapper::toDomain)
                .orElseThrow(() -> new LeadNotFoundException(leadId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeadActivity> findByLeadId(Long leadId) {
        return jpaLeadActivityRepository
                .findAllByRelatedToTypeAndRelatedToIdAndDeletedAtIsNullOrderByCreatedAtDesc("LEAD", leadId)
                .stream()
                .map(LeadActivityJpaMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public LeadActivityStatistics getStatisticsByLeadId(Long leadId) {
        return jpaLeadActivityRepository.getActivityStatistics(leadId);
    }
}
