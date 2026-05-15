package org.example.crm_project.modules.opportunity_management.domain.repository;

import org.example.crm_project.modules.opportunity_management.domain.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Domain Repository Interface (Port) – User.
 */
public interface UserRepository {
    List<User> findAll();
    Optional<User> findById(Integer id);
}
