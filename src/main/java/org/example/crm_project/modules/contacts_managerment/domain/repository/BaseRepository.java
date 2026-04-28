package org.example.crm_project.modules.contacts_managerment.domain.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T, ID> {
    T save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    List<T> findAll(int page, int size);
    long count();
    void deleteById(ID id);
    boolean existsById(ID id);
}
