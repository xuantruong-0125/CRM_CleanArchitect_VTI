package org.example.crm_project.modules.system_managerment.infrastructure.persistence.repository;

import org.example.crm_project.modules.system_managerment.infrastructure.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRoleRepository extends JpaRepository<RoleEntity, Long> {

    boolean existsByName(String name);
}