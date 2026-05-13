package org.example.crm_project.modules.opportunity_management.domain.repository;

import org.example.crm_project.modules.opportunity_management.domain.entity.LossReason;

import java.util.List;
import java.util.Optional;

/**
 * Domain Repository Interface (Port) – LossReason.
 */
public interface LossReasonRepository {
    List<LossReason> findAll();
    Optional<LossReason> findById(Integer id);
    LossReason save(LossReason lossReason);
    void deleteById(Integer id);
}
