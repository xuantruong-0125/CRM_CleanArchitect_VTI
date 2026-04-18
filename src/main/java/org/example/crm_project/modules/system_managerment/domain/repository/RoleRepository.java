package org.example.crm_project.modules.system_managerment.domain.repository;

import org.example.crm_project.modules.system_managerment.domain.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {

    Role save(Role role);

    Optional<Role> findById(Long id);

    List<Role> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    boolean existsByName(String name); // 🔥 quan trọng
}