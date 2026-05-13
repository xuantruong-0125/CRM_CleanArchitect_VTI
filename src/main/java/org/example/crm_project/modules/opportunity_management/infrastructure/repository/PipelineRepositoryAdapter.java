package org.example.crm_project.modules.opportunity_management.infrastructure.repository;

import org.example.crm_project.modules.opportunity_management.domain.entity.Pipeline;
import org.example.crm_project.modules.opportunity_management.domain.repository.PipelineRepository;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.repository.JpaPipelineRepository;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.mapper.PipelineEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repository Adapter – Pipeline.
 */
@Component
@RequiredArgsConstructor
public class PipelineRepositoryAdapter implements PipelineRepository {

    private final JpaPipelineRepository jpaRepository;
    private final PipelineEntityMapper mapper;

    @Override
    public List<Pipeline> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Pipeline> findById(Integer id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Pipeline save(Pipeline pipeline) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(pipeline)));
    }

    @Override
    public void deleteById(Integer id) {
        jpaRepository.deleteById(id);
    }
}
