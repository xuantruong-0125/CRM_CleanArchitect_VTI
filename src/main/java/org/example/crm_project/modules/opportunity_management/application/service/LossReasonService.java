package org.example.crm_project.modules.opportunity_management.application.service;

import org.example.crm_project.modules.opportunity_management.application.dto.LossReasonRequest;
import org.example.crm_project.modules.opportunity_management.application.dto.LossReasonResponse;
import org.example.crm_project.modules.opportunity_management.application.mapper.LossReasonMapper;
import org.example.crm_project.modules.opportunity_management.domain.entity.LossReason;
import org.example.crm_project.modules.opportunity_management.domain.repository.LossReasonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Application Service – Điều phối use case của LossReason.
 */
@Service
@RequiredArgsConstructor
public class LossReasonService {

    private final LossReasonRepository lossReasonRepository;
    private final LossReasonMapper lossReasonMapper;

    public List<LossReasonResponse> getAll() {
        return lossReasonRepository.findAll()
                .stream()
                .map(lossReasonMapper::toResponse)
                .collect(Collectors.toList());
    }

    public LossReasonResponse getById(Integer id) {
        LossReason lr = lossReasonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy LossReason ID: " + id));
        return lossReasonMapper.toResponse(lr);
    }

    public LossReasonResponse create(LossReasonRequest request) {
        if (request.getIsActive() == null) request.setIsActive(false);
        LossReason lr = lossReasonMapper.toDomain(request);
        return lossReasonMapper.toResponse(lossReasonRepository.save(lr));
    }

    public LossReasonResponse update(Integer id, LossReasonRequest request) {
        LossReason existing = lossReasonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy LossReason ID: " + id));
        if (request.getIsActive() == null) request.setIsActive(false);
        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        existing.setIsActive(request.getIsActive());
        return lossReasonMapper.toResponse(lossReasonRepository.save(existing));
    }

    public void delete(Integer id) {
        lossReasonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy LossReason ID: " + id));
        lossReasonRepository.deleteById(id);
    }
}
