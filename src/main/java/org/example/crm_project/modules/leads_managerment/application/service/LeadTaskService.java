package org.example.crm_project.modules.leads_managerment.application.service;

import org.example.crm_project.modules.leads_managerment.application.dto.response.LeadTaskResponse;
import java.util.List;

public interface LeadTaskService {
    List<LeadTaskResponse> getTasksByLeadId(Long leadId);
}