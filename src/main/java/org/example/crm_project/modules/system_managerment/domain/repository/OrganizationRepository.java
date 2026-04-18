package org.example.crm_project.modules.system_managerment.domain.repository;

import org.example.crm_project.modules.system_managerment.domain.entity.Organization;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository {
    Organization save(Organization organization);
//    Organization update(Organization organization);
    void deleteById(Long id);
    Optional<Organization> findById(Long id);
    List<Organization> findAll();
    boolean existsById(Long id);
}
