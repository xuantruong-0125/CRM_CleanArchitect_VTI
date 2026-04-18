package org.example.crm_project.modules.system_managerment.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.system_managerment.domain.entity.Organization;
import org.example.crm_project.modules.system_managerment.domain.repository.OrganizationRepository;
import org.example.crm_project.modules.system_managerment.infrastructure.persistence.mapper.OrganizationJpaMapper;
import org.example.crm_project.modules.system_managerment.infrastructure.persistence.repository.JpaOrganizationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository // 👈 BẮT BUỘC
@RequiredArgsConstructor
public class OrganizationRepositoryImpl implements OrganizationRepository {

    private final JpaOrganizationRepository jpaRepository;

    @Override
    public Organization save(Organization org) {
        return OrganizationJpaMapper.toDomain(
                jpaRepository.save(OrganizationJpaMapper.toEntity(org))
        );
    }

    @Override
    public Optional<Organization> findById(Long id) {
        return jpaRepository.findById(id)
                .map(OrganizationJpaMapper::toDomain);
    }

    @Override
    public List<Organization> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(OrganizationJpaMapper::toDomain)
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
}