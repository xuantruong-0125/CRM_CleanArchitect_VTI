package org.example.crm_project.modules.customers.infrastructure.persistence.jpa;

import org.example.crm_project.modules.customers.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository: CustomerJpaRepository
 * Tương tác trực tiếp với database cho Customer
 */
@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Long> {

    /**
     * Tìm theo email
     */
    Optional<CustomerEntity> findByEmail(String email);

    /**
     * Tìm theo mã khách hàng
     */
    Optional<CustomerEntity> findByCustomerCode(String customerCode);

    /**
     * Tìm theo mã thuế
     */
    Optional<CustomerEntity> findByTaxCode(String taxCode);

    /**
     * Tìm theo số điện thoại
     */
    List<CustomerEntity> findByPhone(String phone);

    /**
     * Tìm theo loại khách hàng (B2B, B2C)
     */
    Page<CustomerEntity> findByType(String type, Pageable pageable);

    /**
     * Tìm theo người phụ trách (sử dụng INDEX để nhanh)
     */
    @Query("SELECT c FROM CustomerEntity c WHERE c.assignedTo = :assignedTo AND c.deletedAt IS NULL")
    Page<CustomerEntity> findByAssignedTo(@Param("assignedTo") Long assignedTo, Pageable pageable);

    /**
     * Tìm theo trạng thái (INDEX)
     */
    @Query("SELECT c FROM CustomerEntity c WHERE c.statusId = :statusId AND c.deletedAt IS NULL")
    Page<CustomerEntity> findByStatusId(@Param("statusId") Long statusId, Pageable pageable);

    /**
     * Tìm theo tier (INDEX)
     */
    @Query("SELECT c FROM CustomerEntity c WHERE c.tierId = :tierId AND c.deletedAt IS NULL")
    Page<CustomerEntity> findByTierId(@Param("tierId") Long tierId, Pageable pageable);

    /**
     * Tìm theo nguồn
     */
    Page<CustomerEntity> findBySourceId(Long sourceId, Pageable pageable);

    /**
     * Lấy danh sách không xóa mềm
     */
    @Query("SELECT c FROM CustomerEntity c WHERE c.deletedAt IS NULL")
    Page<CustomerEntity> findAllActive(Pageable pageable);

    /**
     * Lấy khách hàng cần chăm sóc lại (status = ACTIVE, assignedTo là NULL)
     */
    @Query("SELECT c FROM CustomerEntity c WHERE c.statusId = 1 AND c.assignedTo IS NULL AND c.deletedAt IS NULL")
    List<CustomerEntity> findUnassignedActiveCustomers();

    /**
     * Lấy khách hàng theo range ngày tạo (cho dashboard)
     */
    @Query(value = "SELECT c FROM CustomerEntity c WHERE DATE(c.createdAt) BETWEEN :startDate AND :endDate AND c.deletedAt IS NULL",
            countQuery = "SELECT COUNT(c) FROM CustomerEntity c WHERE DATE(c.createdAt) BETWEEN :startDate AND :endDate AND c.deletedAt IS NULL")
    Page<CustomerEntity> findByDateRange(@Param("startDate") String startDate, @Param("endDate") String endDate, Pageable pageable);

    /**
     * Đếm khách hàng theo trạng thái (cho dashboard/KPI)
     */
    @Query("SELECT COUNT(c) FROM CustomerEntity c WHERE c.statusId = :statusId AND c.deletedAt IS NULL")
    long countByStatusId(@Param("statusId") Long statusId);

    /**
     * Đếm khách hàng theo tier
     */
    @Query("SELECT COUNT(c) FROM CustomerEntity c WHERE c.tierId = :tierId AND c.deletedAt IS NULL")
    long countByTierId(@Param("tierId") Long tierId);
}
