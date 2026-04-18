package org.example.crm_project.infrastructure.persistence.repository;

import org.example.crm_project.infrastructure.persistence.entity.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrganizationRepository extends JpaRepository<OrganizationEntity, Long> {
}