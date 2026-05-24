package org.example.crm_project.modules.leads_managerment.application.service;

import org.example.crm_project.modules.leads_managerment.application.dto.response.LeadActivityStatisticsResponse;

public interface LeadActivityService {

    LeadActivityStatisticsResponse getStatistics(Long leadId);
}
