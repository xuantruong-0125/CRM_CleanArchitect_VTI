package org.example.crm_project.modules.auth.domain.repository;

import org.example.crm_project.modules.auth.domain.entity.AuthUser;

import java.util.Optional;

public interface UserAuthRepository {

    Optional<AuthUser> findByUsername(String username);
}