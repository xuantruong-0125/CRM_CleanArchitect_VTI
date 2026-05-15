package org.example.crm_project.modules.opportunity_management.infrastructure.repository;

import org.example.crm_project.modules.opportunity_management.domain.entity.StageChecklist;
import org.example.crm_project.modules.opportunity_management.domain.repository.StageChecklistRepository;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.repository.JpaStageChecklistRepository;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.mapper.StageChecklistEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repository Adapter – StageChecklist.
 */
@Component
@RequiredArgsConstructor
public class StageChecklistRepositoryAdapter implements StageChecklistRepository {

    private final JpaStageChecklistRepository jpaRepository;
    private final StageChecklistEntityMapper mapper;

    @Override
    public List<StageChecklist> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<StageChecklist> findById(Integer id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public StageChecklist save(StageChecklist sc) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(sc)));
    }

    @Override
    public void deleteById(Integer id) {
        jpaRepository.deleteById(id);
    }
}
