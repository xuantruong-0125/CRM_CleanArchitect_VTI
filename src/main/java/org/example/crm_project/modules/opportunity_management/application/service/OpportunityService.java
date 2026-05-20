package org.example.crm_project.modules.opportunity_management.application.service;

import org.example.crm_project.modules.opportunity_management.application.dto.OpportunityRequest;
import org.example.crm_project.modules.opportunity_management.application.dto.OpportunityResponse;
import org.example.crm_project.modules.opportunity_management.application.mapper.OpportunityMapper;
import org.example.crm_project.modules.opportunity_management.domain.entity.Opportunity;
import org.example.crm_project.modules.opportunity_management.domain.repository.OpportunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Application Service – Điều phối use case của Opportunity.
 *
 * Trách nhiệm:
 *   - Gọi Domain logic (business rules trong Opportunity entity)
 *   - Phối hợp với Domain Repository (port)
 *   - Chuyển đổi Domain ↔ DTO qua Mapper
 *   - KHÔNG chứa business logic trực tiếp
 */
@Service
@RequiredArgsConstructor
public class OpportunityService {

    private final OpportunityRepository opportunityRepository;
    private final OpportunityMapper opportunityMapper;

    /** Lấy toàn bộ danh sách Opportunity. */
    public List<OpportunityResponse> getAll() {
        return opportunityRepository.findAll()
                .stream()
                .map(opportunityMapper::toResponse)
                .collect(Collectors.toList());
    }

    /** Lấy chi tiết theo ID. */
    public OpportunityResponse getById(Integer id) {
        Opportunity opp = opportunityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Opportunity ID: " + id));
        return opportunityMapper.toResponse(opp);
    }

    /** Tạo mới Opportunity – áp dụng business rules từ Domain. */
    public OpportunityResponse create(OpportunityRequest request) {
        Opportunity opp = opportunityMapper.toDomain(request);

        // Gọi Business Logic từ Domain Entity
        opp.applyDefaultHealthStatus();
        opp.recalculateRemainingAmount();

        Opportunity saved = opportunityRepository.save(opp);
        return opportunityMapper.toResponse(saved);
    }

    /** Cập nhật Opportunity – áp dụng business rules từ Domain. */
    public OpportunityResponse update(Integer id, OpportunityRequest request) {
        Opportunity existing = opportunityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Opportunity ID: " + id));

        // Cập nhật các trường từ request lên existing domain object
        existing.setName(request.getName());
        existing.setCustomerId(request.getCustomerId());
        existing.setAssignedUserId(request.getAssignedUserId());
        existing.setPipelineId(request.getPipelineId());
        existing.setStageId(request.getStageId());
        existing.setLossReasonId(request.getLossReasonId());
        existing.setTotalAmount(request.getTotalAmount());
        existing.setDepositAmount(request.getDepositAmount());
        existing.setCurrencyCode(request.getCurrencyCode());
        existing.setExchangeRate(request.getExchangeRate());
        existing.setHealthStatus(request.getHealthStatus());
        existing.setExpectedCloseDate(request.getExpectedCloseDate());

        // Gọi Business Logic từ Domain Entity
        existing.applyDefaultHealthStatus();
        existing.recalculateRemainingAmount();

        Opportunity saved = opportunityRepository.save(existing);
        return opportunityMapper.toResponse(saved);
    }

    /** Xóa Opportunity. */
    public void delete(Integer id) {
        opportunityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Opportunity ID: " + id));
        opportunityRepository.deleteById(id);
    }

    /** Lọc phân trang với nhiều tiêu chí. */
    public Page<OpportunityResponse> filter(String keyword, Integer customerId, Integer assignedUserId,
                                            Integer pipelineId, Integer stageId, String healthStatus,
                                            LocalDate dateFrom, LocalDate dateTo,
                                            String sortField, String sortDir, int page, int size) {
        return opportunityRepository.filter(keyword, customerId, assignedUserId,
                        pipelineId, stageId, healthStatus, dateFrom, dateTo,
                        sortField, sortDir, page, size)
                .map(opportunityMapper::toResponse);
    }

    /** Lấy danh sách tên Opportunity để autocomplete. */
    public List<String> getAllNames() {
        return opportunityRepository.findAll().stream()
                .map(Opportunity::getName)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}
