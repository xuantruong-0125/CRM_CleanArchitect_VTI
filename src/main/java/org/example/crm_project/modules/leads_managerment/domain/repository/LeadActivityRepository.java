package org.example.crm_project.modules.leads_managerment.domain.repository;

import org.example.crm_project.modules.leads_managerment.domain.entity.LeadActivityStatistics;

public interface LeadActivityRepository {

    LeadActivityStatistics getStatisticsByLeadId(Long leadId);
}
