package org.example.crm_project.modules.opportunity_management.application.service;

import org.example.crm_project.modules.opportunity_management.application.dto.StageChecklistRequest;
import org.example.crm_project.modules.opportunity_management.application.dto.StageChecklistResponse;
import org.example.crm_project.modules.opportunity_management.application.mapper.StageChecklistMapper;
import org.example.crm_project.modules.opportunity_management.domain.entity.StageChecklist;
import org.example.crm_project.modules.opportunity_management.domain.repository.StageChecklistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Application Service – Điều phối use case của StageChecklist.
 */
@Service
@RequiredArgsConstructor
public class StageChecklistService {

    private final StageChecklistRepository checklistRepository;
    private final StageChecklistMapper checklistMapper;

    public List<StageChecklistResponse> getAll() {
        return checklistRepository.findAll()
                .stream()
                .map(checklistMapper::toResponse)
                .collect(Collectors.toList());
    }

    public StageChecklistResponse getById(Integer id) {
        StageChecklist sc = checklistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy StageChecklist ID: " + id));
        return checklistMapper.toResponse(sc);
    }

    public StageChecklistResponse create(StageChecklistRequest request) {
        if (request.getIsMandatory() == null) request.setIsMandatory(false);
        StageChecklist sc = checklistMapper.toDomain(request);
        return checklistMapper.toResponse(checklistRepository.save(sc));
    }

    public StageChecklistResponse update(Integer id, StageChecklistRequest request) {
        StageChecklist existing = checklistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy StageChecklist ID: " + id));
        if (request.getIsMandatory() == null) request.setIsMandatory(false);
        existing.setTaskName(request.getTaskName());
        existing.setDescription(request.getDescription());
        existing.setIsMandatory(request.getIsMandatory());
        existing.setSortOrder(request.getSortOrder());
        existing.setStageId(request.getStageId());
        return checklistMapper.toResponse(checklistRepository.save(existing));
    }

    public void delete(Integer id) {
        checklistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy StageChecklist ID: " + id));
        checklistRepository.deleteById(id);
    }
}
