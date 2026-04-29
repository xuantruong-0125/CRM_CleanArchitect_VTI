package org.example.crm_project.modules.products_managerment.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.products_managerment.domain.entity.Price;
import org.example.crm_project.modules.products_managerment.domain.repository.PriceRepository;
import org.example.crm_project.modules.products_managerment.infrastructure.persistence.entity.PriceEntity;
import org.example.crm_project.modules.products_managerment.infrastructure.persistence.mapper.PricePersistenceMapper;
import org.example.crm_project.modules.products_managerment.infrastructure.persistence.repository.PriceJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;

@Repository
@RequiredArgsConstructor
public class PriceRepositoryImpl implements PriceRepository {

    private final PriceJpaRepository jpaRepository;

    @Override
    public Price save(Price price) {
        PriceEntity entity = PricePersistenceMapper.toEntity(price);
        PriceEntity saved = jpaRepository.save(entity);
        return PricePersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Price> findById(Long id) {
        return jpaRepository.findById(id)
                .map(PricePersistenceMapper::toDomain);
    }

    @Override
    public List<Price> findAll() {
        return jpaRepository.findAllActive().stream()
                .map(PricePersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<Price> findAll(int page, int size) {
        return jpaRepository.findAllActive(PageRequest.of(page, size)).stream()
                .map(PricePersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public long count() {
        return jpaRepository.count();
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
    public List<Price> findByProductId(Long productId) {
        return jpaRepository.findByProductId(productId).stream()
                .map(PricePersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<Price> search(String keyword, Long productId, int page, int size) {
        return jpaRepository.search(keyword, productId, PageRequest.of(page, size)).stream()
                .map(PricePersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public long countSearch(String keyword, Long productId) {
        return jpaRepository.countSearch(keyword, productId);
    }
}
