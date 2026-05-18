package org.example.crm_project.modules.opportunity_management.domain.repository;

import org.example.crm_project.modules.opportunity_management.domain.entity.PipelineStage;

import java.util.List;
import java.util.Optional;

/**
 * Domain Repository Interface (Port) – PipelineStage.
 */
public interface PipelineStageRepository {
    List<PipelineStage> findAll();
    Optional<PipelineStage> findById(Integer id);
    List<PipelineStage> findByPipelineId(Integer pipelineId);
    PipelineStage save(PipelineStage stage);
    void saveAll(List<PipelineStage> stages);
    void deleteById(Integer id);
}
