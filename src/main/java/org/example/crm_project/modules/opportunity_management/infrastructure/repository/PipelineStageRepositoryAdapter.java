package org.example.crm_project.modules.opportunity_management.infrastructure.repository;

import org.example.crm_project.modules.opportunity_management.domain.entity.PipelineStage;
import org.example.crm_project.modules.opportunity_management.domain.repository.PipelineStageRepository;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.repository.JpaPipelineStageRepository;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.mapper.PipelineStageEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repository Adapter – PipelineStage.
 */
@Component
@RequiredArgsConstructor
public class PipelineStageRepositoryAdapter implements PipelineStageRepository {

    private final JpaPipelineStageRepository jpaRepository;
    private final PipelineStageEntityMapper mapper;

    @Override
    public List<PipelineStage> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<PipelineStage> findById(Integer id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<PipelineStage> findByPipelineId(Integer pipelineId) {
        return jpaRepository.findByPipelineId(pipelineId).stream()
                .map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public PipelineStage save(PipelineStage stage) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(stage)));
    }

    @Override
    public void saveAll(List<PipelineStage> stages) {
        stages.forEach(s -> jpaRepository.save(mapper.toEntity(s)));
    }

    @Override
    public void deleteById(Integer id) {
        jpaRepository.deleteById(id);
    }
}
