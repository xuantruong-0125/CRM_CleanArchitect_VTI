package org.example.crm_project.modules.customers.domain.repository;

import org.example.crm_project.modules.customers.domain.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Repository Interface: CustomerRepository
 * Định nghĩa các phương thức tương tác với Customer trong Domain
 */
public interface CustomerRepository {

    /**
     * Lưu hoặc cập nhật khách hàng
     */
    Customer save(Customer customer);

    /**
     * Tìm khách hàng theo ID
     */
    Optional<Customer> findById(Long id);

    /**
     * Tìm khách hàng theo mã khách hàng
     */
    Optional<Customer> findByCustomerCode(String customerCode);

    /**
     * Tìm khách hàng theo email
     */
    Optional<Customer> findByEmail(String email);

    /**
     * Tìm khách hàng theo số điện thoại
     */
    List<Customer> findByPhone(String phone);

    /**
     * Lấy danh sách khách hàng phân trang
     */
    Page<Customer> findAll(Pageable pageable);

    /**
     * Lấy danh sách khách hàng theo loại (B2B, B2C)
     */
    Page<Customer> findByType(String type, Pageable pageable);

    /**
     * Lấy danh sách khách hàng theo người phụ trách
     */
    Page<Customer> findByAssignedTo(Long userId, Pageable pageable);

    /**
     * Lấy danh sách khách hàng theo trạng thái
     */
    Page<Customer> findByStatusId(Long statusId, Pageable pageable);

    /**
     * Lấy danh sách khách hàng theo tier
     */
    Page<Customer> findByTierId(Long tierId, Pageable pageable);

    /**
     * Lấy danh sách khách hàng theo nguồn
     */
    Page<Customer> findBySourceId(Long sourceId, Pageable pageable);

    /**
     * Tìm khách hàng theo mã thuế
     */
    Optional<Customer> findByTaxCode(String taxCode);

    /**
     * Xóa khách hàng (soft delete)
     */
    void delete(Long id);

    /**
     * Kiểm tra khách hàng tồn tại
     */
    boolean existsById(Long id);

    /**
     * Lấy tổng số khách hàng
     */
    long count();

    /**
     * Tìm nhiều khách hàng theo danh sách ID (dùng cho batch fetching)
     */
    List<Customer> findByIds(Collection<Long> ids);

    /**
     * Lấy tất cả khách hàng chưa bị xóa (dùng cho dropdown)
     */
    List<Customer> findAllActive();

    /**
     * Tìm kiếm khách hàng theo từ khóa (tên hoặc mã), giới hạn số lượng kết quả
     */
    List<Customer> searchCustomers(String keyword, int limit);
}
