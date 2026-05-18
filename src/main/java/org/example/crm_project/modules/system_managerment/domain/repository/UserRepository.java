package org.example.crm_project.modules.system_managerment.domain.repository;

import org.example.crm_project.modules.system_managerment.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    List<User> findAll();

    void deleteById(Long id);
}