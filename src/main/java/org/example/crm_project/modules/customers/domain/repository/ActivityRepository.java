package org.example.crm_project.modules.customers.domain.repository;

import org.example.crm_project.modules.customers.domain.entity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository Interface: ActivityRepository
 * Định nghĩa các phương thức tương tác với Activity trong Domain
 */
public interface ActivityRepository {

    /**
     * Lưu hoặc cập nhật hoạt động
     */
    Activity save(Activity activity);

    /**
     * Tìm hoạt động theo ID
     */
    Optional<Activity> findById(Long id);

    /**
     * Lấy danh sách hoạt động theo loại và ID
     */
    List<Activity> findByRelatedToTypeAndRelatedToId(String relatedToType, Long relatedToId);

    /**
     * Lấy danh sách hoạt động phân trang
     */
    Page<Activity> findByRelatedToTypeAndRelatedToId(String relatedToType, Long relatedToId, Pageable pageable);

    /**
     * Lấy danh sách hoạt động của người dùng
     */
    Page<Activity> findByPerformedBy(Long userId, Pageable pageable);

    /**
     * Lấy hoạt động trong khoảng thời gian
     */
    List<Activity> findByDateRange(LocalDate startDate, LocalDate endDate);

    /**
     * Xóa hoạt động (soft delete)
     */
    void delete(Long id);

    /**
     * Kiểm tra hoạt động tồn tại
     */
    boolean existsById(Long id);

    /**
     * Lấy tổng số hoạt động
     */
    long count();

    /**
     * Lấy tổng số hoạt động của khách hàng
     */
    long countByRelatedToTypeAndRelatedToId(String relatedToType, Long relatedToId);

    Page<Activity> findByActivityType(String activityType, Pageable pageable);
}
