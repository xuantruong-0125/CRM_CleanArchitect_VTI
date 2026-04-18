package org.example.crm_project.modules.system_managerment.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.system_managerment.domain.entity.Menu;
import org.example.crm_project.modules.system_managerment.domain.repository.MenuRepository;
import org.example.crm_project.modules.system_managerment.infrastructure.persistence.mapper.MenuJpaMapper;
import org.example.crm_project.modules.system_managerment.infrastructure.persistence.repository.JpaMenuRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuRepository {

    private final JpaMenuRepository jpaRepository;

    @Override
    public Menu save(Menu menu) {
        return MenuJpaMapper.toDomain(
                jpaRepository.save(MenuJpaMapper.toEntity(menu))
        );
    }

    @Override
    public Optional<Menu> findById(Long id) {
        return jpaRepository.findById(id)
                .map(MenuJpaMapper::toDomain);
    }

    @Override
    public List<Menu> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(MenuJpaMapper::toDomain)
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