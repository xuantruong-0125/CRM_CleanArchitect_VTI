package org.example.crm_project.modules.system_managerment.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.system_managerment.domain.entity.Role;
import org.example.crm_project.modules.system_managerment.domain.repository.RoleRepository;
import org.example.crm_project.modules.system_managerment.infrastructure.persistence.mapper.RoleJpaMapper;
import org.example.crm_project.modules.system_managerment.infrastructure.persistence.repository.JpaRoleRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final JpaRoleRepository jpaRepository;

    @Override
    public Role save(Role role) {
        return RoleJpaMapper.toDomain(
                jpaRepository.save(RoleJpaMapper.toEntity(role))
        );
    }

    @Override
    public Optional<Role> findById(Long id) {
        return jpaRepository.findById(id)
                .map(RoleJpaMapper::toDomain);
    }

    @Override
    public List<Role> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(RoleJpaMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }
}