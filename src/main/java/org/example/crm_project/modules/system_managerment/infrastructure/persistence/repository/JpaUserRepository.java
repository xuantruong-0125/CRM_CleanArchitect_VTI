package org.example.crm_project.modules.system_managerment.infrastructure.persistence.repository;

import org.example.crm_project.modules.system_managerment.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Integer> {
}
