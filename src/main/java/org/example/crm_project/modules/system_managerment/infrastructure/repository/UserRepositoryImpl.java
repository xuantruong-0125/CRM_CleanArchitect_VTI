package org.example.crm_project.modules.system_managerment.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.system_managerment.domain.entity.User;
import org.example.crm_project.modules.system_managerment.domain.repository.UserRepository;
import org.example.crm_project.modules.system_managerment.infrastructure.persistence.mapper.UserJpaMapper;
import org.example.crm_project.modules.system_managerment.infrastructure.persistence.repository.JpaUserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaRepository;

    @Override
    public User save(User user) {
        return UserJpaMapper.toDomain(
                jpaRepository.save(UserJpaMapper.toEntity(user))
        );
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaRepository.findById(id)
                .filter(u -> u.getDeletedAt() == null)
                .map(UserJpaMapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaRepository.findByUsername(username)
                .map(UserJpaMapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return jpaRepository.findAllActive()
                .stream()
                .map(UserJpaMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}