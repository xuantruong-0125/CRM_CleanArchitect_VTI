package org.example.crm_project.modules.leads_managerment.application.service;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.leads_managerment.application.dto.response.LeadActivityStatisticsResponse;
import org.example.crm_project.modules.leads_managerment.domain.entity.Lead;
import org.example.crm_project.modules.leads_managerment.domain.entity.LeadActivityStatistics;
import org.example.crm_project.modules.leads_managerment.domain.exception.InvalidLeadException;
import org.example.crm_project.modules.leads_managerment.domain.exception.LeadNotFoundException;
import org.example.crm_project.modules.leads_managerment.domain.repository.LeadActivityRepository;
import org.example.crm_project.modules.leads_managerment.domain.repository.LeadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LeadActivityServiceImpl implements LeadActivityService {

    private final LeadActivityRepository leadActivityRepository;
    private final LeadRepository leadRepository;

    @Override
    @Transactional(readOnly = true)
    public LeadActivityStatisticsResponse getStatistics(Long leadId) {
        validateLeadId(leadId);
        getExistingLead(leadId);

        LeadActivityStatistics statistics = leadActivityRepository.getStatisticsByLeadId(leadId);
        return LeadActivityStatisticsResponse.builder()
                .callCount(statistics.getCallCount())
                .meetingCount(statistics.getMeetingCount())
                .emailCount(statistics.getEmailCount())
                .totalCount(statistics.getTotalCount())
                .build();
    }

    private Lead getExistingLead(Long leadId) {
        return leadRepository.findById(leadId)
                .orElseThrow(() -> new LeadNotFoundException(leadId));
    }

    private void validateLeadId(Long leadId) {
        if (leadId == null || leadId <= 0) {
            throw new InvalidLeadException("leadId must be a positive number");
        }
    }
}
