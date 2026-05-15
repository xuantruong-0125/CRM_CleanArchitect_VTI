package org.example.crm_project.modules.customers.infrastructure.persistence.jpa;

import org.example.crm_project.modules.customers.infrastructure.persistence.entity.ContactEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository: ContactJpaRepository
 */
@Repository
public interface ContactJpaRepository extends JpaRepository<ContactEntity, Long> {

    /**
     * Tìm theo khách hàng
     */
    List<ContactEntity> findByCustomerId(Long customerId);

    /**
     * Tìm theo khách hàng phân trang
     */
    Page<ContactEntity> findByCustomerId(Long customerId, Pageable pageable);

    /**
     * Tìm người liên hệ chính
     */
    @Query("SELECT c FROM ContactEntity c WHERE c.customerId = :customerId AND c.isPrimary = true AND c.deletedAt IS NULL")
    Optional<ContactEntity> findPrimaryContact(@Param("customerId") Long customerId);

    /**
     * Tìm theo email
     */
    Optional<ContactEntity> findByEmail(String email);

    /**
     * Tìm theo số điện thoại
     */
    List<ContactEntity> findByPhone(String phone);

    /**
     * Đếm người liên hệ của khách hàng
     */
    long countByCustomerId(Long customerId);

    /**
     * Xóa tất cả người liên hệ của khách hàng
     */
    void deleteByCustomerId(Long customerId);
}
