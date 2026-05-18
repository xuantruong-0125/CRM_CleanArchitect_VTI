package org.example.crm_project.modules.system_managerment.domain.repository;

import org.example.crm_project.modules.system_managerment.domain.entity.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuRepository {

    Menu save(Menu menu);

    Optional<Menu> findById(Long id);

    List<Menu> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);
}