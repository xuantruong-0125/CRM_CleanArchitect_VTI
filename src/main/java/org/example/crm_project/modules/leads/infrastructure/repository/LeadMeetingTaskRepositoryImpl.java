package org.example.crm_project.modules.leads.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.leads.domain.entity.LeadMeetingTask;
import org.example.crm_project.modules.leads.domain.exception.LeadNotFoundException;
import org.example.crm_project.modules.leads.domain.repository.LeadMeetingTaskRepository;
import org.example.crm_project.modules.leads.infrastructure.persistence.mapper.LeadMeetingTaskJpaMapper;
import org.example.crm_project.modules.leads.infrastructure.persistence.repository.JpaLeadMeetingTaskRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class LeadMeetingTaskRepositoryImpl implements LeadMeetingTaskRepository {

    private final JpaLeadMeetingTaskRepository jpaLeadMeetingTaskRepository;

    @Override
    @Transactional
    public LeadMeetingTask save(LeadMeetingTask meetingTask) {
        return LeadMeetingTaskJpaMapper.toDomain(
                jpaLeadMeetingTaskRepository.save(LeadMeetingTaskJpaMapper.toEntity(meetingTask))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public LeadMeetingTask findByIdAndLeadId(Long meetingTaskId, Long leadId) {
        return jpaLeadMeetingTaskRepository
                .findByIdAndRelatedToTypeAndRelatedToIdAndDeletedAtIsNull(meetingTaskId, "LEAD", leadId)
                .map(LeadMeetingTaskJpaMapper::toDomain)
                .orElseThrow(() -> new LeadNotFoundException(leadId));
    }
}