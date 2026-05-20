package org.example.crm_project.modules.opportunity_management.infrastructure.repository;

import org.example.crm_project.modules.opportunity_management.domain.entity.LossReason;
import org.example.crm_project.modules.opportunity_management.domain.repository.LossReasonRepository;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.repository.JpaLossReasonRepository;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.mapper.LossReasonEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repository Adapter – LossReason.
 */
@Component
@RequiredArgsConstructor
public class LossReasonRepositoryAdapter implements LossReasonRepository {

    private final JpaLossReasonRepository jpaRepository;
    private final LossReasonEntityMapper mapper;

    @Override
    public List<LossReason> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<LossReason> findById(Integer id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public LossReason save(LossReason lr) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(lr)));
    }

    @Override
    public void deleteById(Integer id) {
        jpaRepository.deleteById(id);
    }
}
