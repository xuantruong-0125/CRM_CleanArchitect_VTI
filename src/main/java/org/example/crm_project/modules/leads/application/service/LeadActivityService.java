package org.example.crm_project.modules.leads.application.service;

import org.example.crm_project.modules.leads.application.dto.request.CreateLeadActivityRequest;
import org.example.crm_project.modules.leads.application.dto.request.UpdateLeadActivityRequest;
import org.example.crm_project.modules.leads.application.dto.response.LeadActivityResponse;
import org.example.crm_project.modules.leads.application.dto.response.LeadActivityStatisticsResponse;

import java.util.List;

public interface LeadActivityService {

    LeadActivityResponse create(Long leadId, CreateLeadActivityRequest request);

    LeadActivityResponse update(Long leadId, Long activityId, UpdateLeadActivityRequest request);

    List<LeadActivityResponse> getByLeadId(Long leadId);

    LeadActivityStatisticsResponse getStatistics(Long leadId);
}