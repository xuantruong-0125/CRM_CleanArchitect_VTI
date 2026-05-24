package org.example.crm_project.modules.system_managerment.domain.repository;

import org.example.crm_project.modules.system_managerment.domain.entity.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuRepository {

    // ===== COMMAND =====
    Menu save(Menu menu);
    void deleteById(Long id); // hoặc soft delete

    // ===== QUERY =====
    Optional<Menu> findById(Long id);
    Optional<Menu> findByCode(String code);

    List<Menu> findAll();

    List<Menu> findByParentId(Long parentId);
    List<Menu> findRootMenus();

    boolean existsById(Long id);
    boolean existsByCode(String code);
}