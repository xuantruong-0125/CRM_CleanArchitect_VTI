package org.example.crm_project.modules.customers.application.service;

import org.example.crm_project.modules.customers.application.dto.request.CreateOpportunityDTO;
import org.example.crm_project.modules.customers.application.dto.response.OpportunityResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OpportunityService {
    OpportunityResponseDTO createOpportunity(CreateOpportunityDTO createDTO);
    OpportunityResponseDTO getOpportunityById(Long id);
    Page<OpportunityResponseDTO> getOpportunitiesByCustomer(Long customerId, Pageable pageable);
    Page<OpportunityResponseDTO> getOpportunitiesByAssignedUser(Long userId, Pageable pageable);
    Page<OpportunityResponseDTO> getOpportunitiesByHealthStatus(String healthStatus, Pageable pageable);
    OpportunityResponseDTO updateOpportunity(Long id, CreateOpportunityDTO createDTO);
    void deleteOpportunity(Long id);
    long countOpportunities();
}
