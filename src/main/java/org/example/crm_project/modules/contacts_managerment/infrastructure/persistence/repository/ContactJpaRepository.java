package org.example.crm_project.modules.contacts_managerment.infrastructure.persistence.repository;

import org.example.crm_project.modules.contacts_managerment.infrastructure.persistence.entity.ContactEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactJpaRepository extends JpaRepository<ContactEntity, Long> {
    
    @Query("SELECT c FROM ContactEntity c WHERE c.isActive = true")
    List<ContactEntity> findAllActive();

    @Query("SELECT c FROM ContactEntity c WHERE c.isActive = true")
    Page<ContactEntity> findAllActive(Pageable pageable);

    @Query("SELECT c FROM ContactEntity c WHERE c.isActive = true AND " +
           "(:keyword IS NULL OR :keyword = '' OR " +
           "LOWER(c.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.phone) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<ContactEntity> searchContacts(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT COUNT(c) FROM ContactEntity c WHERE c.isActive = true AND " +
           "(:keyword IS NULL OR :keyword = '' OR " +
           "LOWER(c.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.phone) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    long countSearchContacts(@Param("keyword") String keyword);
}
