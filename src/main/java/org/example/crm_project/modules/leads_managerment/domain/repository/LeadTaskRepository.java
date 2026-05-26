package org.example.crm_project.modules.leads_managerment.domain.repository;

import org.example.crm_project.modules.leads_managerment.domain.entity.LeadTask;
import java.util.List;

public interface LeadTaskRepository {
    List<LeadTask> findByLeadId(Long leadId);
}