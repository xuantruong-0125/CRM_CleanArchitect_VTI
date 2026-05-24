package org.example.crm_project.modules.leads_managerment.application.service;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.leads_managerment.application.dto.response.LeadTaskResponse;
import org.example.crm_project.modules.leads_managerment.application.mapper.LeadTaskMapper;
import org.example.crm_project.modules.leads_managerment.domain.entity.Lead;
import org.example.crm_project.modules.leads_managerment.domain.entity.LeadTask;
import org.example.crm_project.modules.leads_managerment.domain.exception.InvalidLeadException;
import org.example.crm_project.modules.leads_managerment.domain.exception.LeadNotFoundException;
import org.example.crm_project.modules.leads_managerment.domain.repository.LeadReferenceRepository;
import org.example.crm_project.modules.leads_managerment.domain.repository.LeadRepository;
import org.example.crm_project.modules.leads_managerment.domain.repository.LeadTaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LeadTaskServiceImpl implements LeadTaskService {
    private final LeadTaskRepository leadTaskRepository;
    private final LeadRepository leadRepository;
    private final LeadReferenceRepository leadReferenceRepository;
    @Override
    @Transactional(readOnly = true)
    public List<LeadTaskResponse> getTasksByLeadId(Long leadId) {
        validateLeadId(leadId);
        getExistingLead(leadId);
        List<LeadTask> tasks = leadTaskRepository.findByLeadId(leadId);
        return tasks.stream()
                .map(task -> {
                    String assigneeName = null;
                    if (task.getAssignedTo() != null) {
                        assigneeName = leadReferenceRepository.findUserFullNameById(task.getAssignedTo());
                    }
                    return LeadTaskMapper.toResponse(task, assigneeName);
                })
                .toList();
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