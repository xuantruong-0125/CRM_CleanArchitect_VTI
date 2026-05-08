package org.example.crm_project.modules.kpi_management.domain.repository;

import org.example.crm_project.modules.kpi_management.domain.entity.KpiConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface KpiConfigRepository {
    Page<KpiConfig> findAll(Pageable pageable);
    Page<KpiConfig> search(String keyword, Pageable pageable);
    Optional<KpiConfig> findById(Integer id);
    KpiConfig save(KpiConfig kpiConfig);
    void delete(KpiConfig kpiConfig);
}
