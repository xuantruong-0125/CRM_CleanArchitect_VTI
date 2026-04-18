package org.example.crm_project.modules.system_managerment.infrastructure.persistence.repository;

import org.example.crm_project.modules.system_managerment.infrastructure.persistence.entity.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrganizationRepository extends JpaRepository<OrganizationEntity, Long> {
}