package org.example.crm_project.modules.opportunity_management.infrastructure.repository;

import org.example.crm_project.modules.opportunity_management.domain.entity.User;
import org.example.crm_project.modules.opportunity_management.domain.repository.UserRepository;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.repository.JpaOpportunityUserRepository;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.mapper.UserEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repository Adapter – User.
 */
@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final JpaOpportunityUserRepository jpaRepository;
    private final UserEntityMapper mapper;

    @Override
    public List<User> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(Integer id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
}
