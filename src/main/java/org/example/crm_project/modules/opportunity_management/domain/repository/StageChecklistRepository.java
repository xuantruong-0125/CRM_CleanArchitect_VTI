package org.example.crm_project.modules.opportunity_management.domain.repository;

import org.example.crm_project.modules.opportunity_management.domain.entity.StageChecklist;

import java.util.List;
import java.util.Optional;

/**
 * Domain Repository Interface (Port) – StageChecklist.
 */
public interface StageChecklistRepository {
    List<StageChecklist> findAll();
    Optional<StageChecklist> findById(Integer id);
    StageChecklist save(StageChecklist checklist);
    void deleteById(Integer id);
}
