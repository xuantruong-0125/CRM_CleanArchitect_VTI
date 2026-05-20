package org.example.crm_project.modules.opportunity_management.domain.repository;

import org.example.crm_project.modules.opportunity_management.domain.entity.Pipeline;

import java.util.List;
import java.util.Optional;

/**
 * Domain Repository Interface (Port) – Pipeline.
 */
public interface PipelineRepository {
    List<Pipeline> findAll();
    Optional<Pipeline> findById(Integer id);
    Pipeline save(Pipeline pipeline);
    void deleteById(Integer id);
}
