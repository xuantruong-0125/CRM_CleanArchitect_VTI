package org.example.crm_project.modules.contacts_managerment.infrastructure.persistence.repository;

import org.example.crm_project.modules.contacts_managerment.domain.entity.CustomerType;
import org.example.crm_project.modules.contacts_managerment.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, Long> {

    @Query("SELECT c FROM CustomerEntity c WHERE c.deletedAt IS NULL")
    List<CustomerEntity> findAllActive();

    @Query("SELECT c FROM CustomerEntity c WHERE c.deletedAt IS NULL")
    Page<CustomerEntity> findAllActive(Pageable pageable);

    @Query("SELECT c FROM CustomerEntity c WHERE c.deletedAt IS NULL AND " +
           "(:type IS NULL OR c.type = :type) AND " +
           "(:keyword IS NULL OR :keyword = '' OR " +
           "LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.customerCode) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.phone) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "ORDER BY c.createdAt DESC")
    Page<CustomerEntity> searchCustomers(@Param("keyword") String keyword, 
                                         @Param("type") CustomerType type, 
                                         Pageable pageable);

    @Query("SELECT COUNT(c) FROM CustomerEntity c WHERE c.deletedAt IS NULL AND " +
           "(:type IS NULL OR c.type = :type) AND " +
           "(:keyword IS NULL OR :keyword = '' OR " +
           "LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.customerCode) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.phone) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    long countSearchCustomers(@Param("keyword") String keyword, 
                              @Param("type") CustomerType type);
}
