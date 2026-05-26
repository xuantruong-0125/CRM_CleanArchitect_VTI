package org.example.crm_project.modules.opportunity_management.application.service;
import org.example.crm_project.modules.opportunity_management.application.dto.PipelineStageRequest;
import org.example.crm_project.modules.opportunity_management.application.dto.PipelineStageResponse;
import org.example.crm_project.modules.opportunity_management.application.mapper.PipelineStageMapper;
import org.example.crm_project.modules.opportunity_management.domain.entity.PipelineStage;
import org.example.crm_project.modules.opportunity_management.domain.repository.PipelineStageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Application Service – Điều phối use case của PipelineStage.
 * Gọi domain business logic: tính lại probability sau mỗi thao tác CUD.
 */
@Service
@RequiredArgsConstructor
public class PipelineStageService {

    private final PipelineStageRepository stageRepository;
    private final PipelineStageMapper stageMapper;

    public List<PipelineStageResponse> getAll() {
        return stageRepository.findAll()
                .stream()
                .map(stageMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<PipelineStageResponse> getByPipelineId(Integer pipelineId) {
        return stageRepository.findByPipelineId(pipelineId)
                .stream()
                .map(stageMapper::toResponse)
                .collect(Collectors.toList());
    }

    public PipelineStageResponse getById(Integer id) {
        PipelineStage stage = stageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy PipelineStage ID: " + id));
        return stageMapper.toResponse(stage);
    }

    public PipelineStageResponse create(PipelineStageRequest request) {
        PipelineStage stage = stageMapper.toDomain(request);
        PipelineStage saved = stageRepository.save(stage);

        // Business Logic: tính lại probability sau khi thêm mới
        if (saved.getPipelineId() != null) {
            recalculateProbabilities(saved.getPipelineId());
        }
        return stageMapper.toResponse(saved);
    }

    public PipelineStageResponse update(Integer id, PipelineStageRequest request) {
        PipelineStage existing = stageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy PipelineStage ID: " + id));

        Integer oldPipelineId = existing.getPipelineId();

        existing.setStageName(request.getStageName());
        existing.setMaxDaysAllowed(request.getMaxDaysAllowed());
        existing.setSortOrder(request.getSortOrder());
        existing.setPipelineId(request.getPipelineId());

        PipelineStage saved = stageRepository.save(existing);

        // Business Logic: tính lại probability cho cả pipeline cũ và mới nếu thay đổi
        Integer newPipelineId = saved.getPipelineId();
        if (oldPipelineId != null && !oldPipelineId.equals(newPipelineId)) {
            recalculateProbabilities(oldPipelineId);
        }
        if (newPipelineId != null) {
            recalculateProbabilities(newPipelineId);
        }

        return stageMapper.toResponse(saved);
    }

    public void delete(Integer id) {
        PipelineStage existing = stageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy PipelineStage ID: " + id));
        Integer pipelineId = existing.getPipelineId();
        stageRepository.deleteById(id);

        // Business Logic: tính lại probability sau khi xóa
        if (pipelineId != null) {
            recalculateProbabilities(pipelineId);
        }
    }

    /**
     * Gọi Domain Business Logic: phân bổ lại probability đều cho tất cả stages trong pipeline.
     */
    private void recalculateProbabilities(Integer pipelineId) {
        List<PipelineStage> stages = stageRepository.findByPipelineId(pipelineId);
        // Gọi static domain method
        PipelineStage.recalculateProbabilities(stages);
        stageRepository.saveAll(stages);
    }
}
