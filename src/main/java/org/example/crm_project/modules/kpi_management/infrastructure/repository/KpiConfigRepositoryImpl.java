package org.example.crm_project.modules.kpi_management.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.kpi_management.domain.entity.KpiConfig;
import org.example.crm_project.modules.kpi_management.domain.repository.KpiConfigRepository;
import org.example.crm_project.modules.kpi_management.infrastructure.persistence.entity.KpiConfigEntity;
import org.example.crm_project.modules.kpi_management.infrastructure.persistence.mapper.KpiConfigJpaMapper;
import org.example.crm_project.modules.kpi_management.infrastructure.persistence.repository.JpaKpiConfigRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class KpiConfigRepositoryImpl implements KpiConfigRepository {
    private final JpaKpiConfigRepository jpaRepository;
    private final KpiConfigJpaMapper mapper;

    @Override
    public Page<KpiConfig> findAll(Pageable pageable) {
        return jpaRepository.findAllByDeletedAtIsNull(pageable)
                .map(mapper::toDomain);
    }

    @Override
    public Page<KpiConfig> search(String keyword, Pageable pageable) {
        return jpaRepository.findAllByNameContainingIgnoreCaseAndDeletedAtIsNull(keyword, pageable)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<KpiConfig> findById(Integer id) {
        return jpaRepository.findByIdAndDeletedAtIsNull(id)
                .map(mapper::toDomain);
    }

    @Override
    public KpiConfig save(KpiConfig kpiConfig) {
        KpiConfigEntity entity = mapper.toEntity(kpiConfig);
        KpiConfigEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public void delete(KpiConfig kpiConfig) {
        jpaRepository.save(mapper.toEntity(kpiConfig));
    }
}
