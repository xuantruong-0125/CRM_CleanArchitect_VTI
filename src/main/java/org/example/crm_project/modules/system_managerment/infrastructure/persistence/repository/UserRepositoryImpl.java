package org.example.crm_project.modules.system_managerment.infrastructure.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.system_managerment.application.mapper.UserMapper;
import org.example.crm_project.modules.system_managerment.domain.entity.User;
import org.example.crm_project.modules.system_managerment.domain.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll().stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(Integer id) {
        return jpaUserRepository.findById(id)
                .map(UserMapper::toDomain);
    }
}
