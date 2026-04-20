package org.example.crm_project.modules.leads.domain.repository;

import org.example.crm_project.modules.leads.domain.entity.LeadActivity;
import org.example.crm_project.modules.leads.domain.entity.LeadActivityStatistics;

import java.util.List;

public interface LeadActivityRepository {

    LeadActivity save(LeadActivity leadActivity);

    LeadActivity findByIdAndLeadId(Long activityId, Long leadId);

    List<LeadActivity> findByLeadId(Long leadId);

    LeadActivityStatistics getStatisticsByLeadId(Long leadId);
}