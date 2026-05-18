package org.example.crm_project.modules.opportunity_management.application.service;

import org.example.crm_project.modules.opportunity_management.application.dto.PipelineRequest;
import org.example.crm_project.modules.opportunity_management.application.dto.PipelineResponse;
import org.example.crm_project.modules.opportunity_management.application.mapper.PipelineMapper;
import org.example.crm_project.modules.opportunity_management.domain.entity.Pipeline;
import org.example.crm_project.modules.opportunity_management.domain.repository.PipelineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Application Service – Điều phối use case của Pipeline.
 */
@Service
@RequiredArgsConstructor
public class PipelineService {

    private final PipelineRepository pipelineRepository;
    private final PipelineMapper pipelineMapper;

    public List<PipelineResponse> getAll() {
        return pipelineRepository.findAll()
                .stream()
                .map(pipelineMapper::toResponse)
                .collect(Collectors.toList());
    }

    public PipelineResponse getById(Integer id) {
        Pipeline pipeline = pipelineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Pipeline ID: " + id));
        return pipelineMapper.toResponse(pipeline);
    }

    public PipelineResponse create(PipelineRequest request) {
        Pipeline pipeline = pipelineMapper.toDomain(request);
        Pipeline saved = pipelineRepository.save(pipeline);
        return pipelineMapper.toResponse(saved);
    }

    public PipelineResponse update(Integer id, PipelineRequest request) {
        Pipeline existing = pipelineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Pipeline ID: " + id));
        existing.setName(request.getName());
        Pipeline saved = pipelineRepository.save(existing);
        return pipelineMapper.toResponse(saved);
    }

    public void delete(Integer id) {
        pipelineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Pipeline ID: " + id));
        pipelineRepository.deleteById(id);
    }
}
