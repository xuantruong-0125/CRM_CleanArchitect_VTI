package org.example.crm_project.modules.system_managerment.infrastructure.persistence.repository;

import org.example.crm_project.modules.system_managerment.infrastructure.persistence.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaMenuRepository extends JpaRepository<MenuEntity, Long> {
    boolean existsByCode(String code);
    Optional<MenuEntity> findByCode(String code);
    List<MenuEntity> findByParentId(Long parentId);
    List<MenuEntity> findByParentIdIsNull();

}