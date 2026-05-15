package org.example.crm_project.modules.customers.application.service.impl;

import org.example.crm_project.modules.customers.application.dto.request.CreateOpportunityDTO;
import org.example.crm_project.modules.customers.application.dto.response.OpportunityResponseDTO;
import org.example.crm_project.modules.customers.application.mapper.OpportunityMapper;
import org.example.crm_project.modules.customers.application.service.OpportunityService;
import org.example.crm_project.modules.customers.domain.entity.Opportunity;
import org.example.crm_project.modules.customers.domain.repository.OpportunityRepository;
import org.example.crm_project.modules.customers.domain.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation: OpportunityServiceImpl
 * Business logic for Opportunity management
 */
@Service
@Transactional
public class OpportunityServiceImpl implements OpportunityService {

    private final OpportunityRepository opportunityRepository;
    private final OpportunityMapper opportunityMapper;

    public OpportunityServiceImpl(OpportunityRepository opportunityRepository, OpportunityMapper opportunityMapper) {
        this.opportunityRepository = opportunityRepository;
        this.opportunityMapper = opportunityMapper;
    }

    @Override
    public OpportunityResponseDTO createOpportunity(CreateOpportunityDTO createDTO) {
        Opportunity opportunity = opportunityMapper.toEntity(createDTO);
        Opportunity saved = opportunityRepository.save(opportunity);
        return opportunityMapper.toResponseDTO(saved);
    }

    @Override
    public OpportunityResponseDTO getOpportunityById(Long id) {
        Opportunity opportunity = opportunityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cơ hội không tìm thấy: " + id));
        return opportunityMapper.toResponseDTO(opportunity);
    }

    @Override
    public Page<OpportunityResponseDTO> getOpportunitiesByCustomer(Long customerId, Pageable pageable) {
        Page<Opportunity> opportunities = opportunityRepository.findByCustomerId(customerId, pageable);
        return opportunities.map(opportunityMapper::toResponseDTO);
    }

    @Override
    public Page<OpportunityResponseDTO> getOpportunitiesByAssignedUser(Long userId, Pageable pageable) {
        Page<Opportunity> opportunities = opportunityRepository.findByAssignedUserId(userId, pageable);
        return opportunities.map(opportunityMapper::toResponseDTO);
    }

    @Override
    public Page<OpportunityResponseDTO> getOpportunitiesByHealthStatus(String healthStatus, Pageable pageable) {
        Page<Opportunity> opportunities = opportunityRepository.findByHealthStatus(healthStatus, pageable);
        return opportunities.map(opportunityMapper::toResponseDTO);
    }

    @Override
    public OpportunityResponseDTO updateOpportunity(Long id, CreateOpportunityDTO createDTO) {
        Opportunity opportunity = opportunityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cơ hội không tìm thấy: " + id));
        opportunityMapper.updateEntityFromDTO(opportunity, createDTO);
        Opportunity updated = opportunityRepository.save(opportunity);
        return opportunityMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteOpportunity(Long id) {
        if (!opportunityRepository.existsById(id)) {
            throw new EntityNotFoundException("Cơ hội không tìm thấy: " + id);
        }
        opportunityRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countOpportunities() {
        return opportunityRepository.count();
    }
}
