package org.example.crm_project.modules.activity_management.domain.entity;

import java.time.LocalDateTime;

import org.example.crm_project.modules.activity_management.domain.constant.ActivityStatus;
import org.example.crm_project.modules.activity_management.domain.constant.ActivityType;

public class Activity {

    private Long id;
    private ActivityType activityType;
    private String subject;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime completedAt;
    private String outcome;
    private String relatedToType;
    private Long relatedToId;
    private Long performedBy;
    private ActivityStatus status;
    private boolean isImportant;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long organizationId;

    public Activity() {
    }

    public Activity(Long id, ActivityType activityType, String subject, String description, LocalDateTime startDate,
            LocalDateTime endDate, LocalDateTime completedAt, String outcome, String relatedToType, Long relatedToId,
            Long performedBy, ActivityStatus status, boolean isImportant,
            Long organizationId, LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.activityType = activityType;
        this.subject = subject;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.completedAt = completedAt;
        this.outcome = outcome;
        this.relatedToType = relatedToType;
        this.relatedToId = relatedToId;
        this.performedBy = performedBy;
        this.status = status;
        this.isImportant = isImportant;

        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        this.organizationId = organizationId;
    }
    // ===== BUSINESS LOGIC =====

    public void assignPerformedBy(Long userId) {
        this.performedBy = userId;
    }
    public void assignOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }
    
    public Long getOrganizationId() {
        return organizationId;
    }

    // Hoàn thành một hoạt động
    public void complete(String outcome) {
        this.status = ActivityStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
        this.outcome = outcome;
    }

    // Thay đổi thời gian (cần validate end > start)
    public void reschedule(LocalDateTime newStart, LocalDateTime newEnd) {
        validateDates(newStart, newEnd);
        this.startDate = newStart;
        this.endDate = newEnd;
    }

    public void markAsImportant(boolean important) {
        this.isImportant = important;
    }

    // ===== VALIDATION =====
    private void validateSubject(String subject) {
        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("Subject cannot be empty");
        }
    }

    private void validateDates(LocalDateTime start, LocalDateTime end) {
        if (start != null && end != null && end.isBefore(start)) {
            throw new IllegalArgumentException("End date must be after start date");
        }
    }

    public Long getId() {
        return id;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public String getOutcome() {
        return outcome;
    }

    public String getRelatedToType() {
        return relatedToType;
    }

    public Long getRelatedToId() {
        return relatedToId;
    }

    public Long getPerformedBy() {
        return performedBy;
    }

    public ActivityStatus getStatus() {
        return status;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // ===== CONSTRUCTOR DÙNG ĐỂ TẠO MỚI (Từ Service gọi) =====
    public Activity(ActivityType activityType, String subject, String description,
            String relatedToType,
            Long relatedToId, Long performedBy, LocalDateTime startDate, LocalDateTime endDate) {

        // Luôn gọi hàm validate để đảm bảo dữ liệu đầu vào chuẩn ngay từ "lõi"
        validateSubject(subject);
        validateDates(startDate, endDate);

        this.activityType = activityType;
        this.subject = subject;
        this.description = description;
        this.relatedToType = relatedToType;
        this.relatedToId = relatedToId;
        this.performedBy = performedBy;
        this.startDate = startDate;
        this.endDate = endDate;

        // Các giá trị mặc định khi một Activity mới được tạo ra
        this.status = ActivityStatus.PLANNED;
        this.isImportant = false;
    }

    // ===== BUILDER PATTERN (Pure Java) =====
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private ActivityType activityType;
        private String subject;
        private String description;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private LocalDateTime completedAt;
        private String outcome;
        private String relatedToType;
        private Long relatedToId;
        private Long performedBy;
        private ActivityStatus status;
        private boolean isImportant;

        private Long organizationId;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder activityType(ActivityType activityType) {
            this.activityType = activityType;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder startDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder completedAt(LocalDateTime completedAt) {
            this.completedAt = completedAt;
            return this;
        }

        public Builder outcome(String outcome) {
            this.outcome = outcome;
            return this;
        }

        public Builder relatedToType(String relatedToType) {
            this.relatedToType = relatedToType;
            return this;
        }

        public Builder relatedToId(Long relatedToId) {
            this.relatedToId = relatedToId;
            return this;
        }

        public Builder performedBy(Long performedBy) {
            this.performedBy = performedBy;
            return this;
        }

        public Builder status(ActivityStatus status) {
            this.status = status;
            return this;
        }

        public Builder isImportant(boolean isImportant) {
            this.isImportant = isImportant;
            return this;
        }


        public Builder organizationId(Long organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public Activity build() {
            // Sử dụng constructor đầy đủ tham số đã có sẵn của Duy để tạo đối tượng
            return new Activity(id, activityType, subject, description, startDate, endDate,
                    completedAt, outcome, relatedToType, relatedToId,
                    performedBy, status, isImportant, organizationId,null, null);
        }
    }

    // ===== BUSINESS LOGIC =====
    public void updateInfo(String subject, String description, ActivityStatus status,
            LocalDateTime startDate, LocalDateTime endDate,
            LocalDateTime completedAt, String outcome, Boolean important) {

        validateSubject(subject);
        this.subject = subject;
        this.description = description;

        if (startDate != null)
            this.startDate = startDate;
        if (endDate != null)
            this.endDate = endDate;
        if (outcome != null)
            this.outcome = outcome;
        if (important != null)
            this.isImportant = important;

        // 1. Cập nhật status trước
        if (status != null) {
            this.status = status;
        }

        // 2. XỬ LÝ NGHIỆP VỤ COMPLETED_AT
        if (this.status == ActivityStatus.COMPLETED) {
            if (completedAt != null) {
                this.completedAt = completedAt;
            } else if (this.completedAt == null) {
                this.completedAt = LocalDateTime.now();
            }
        } else {

            this.completedAt = null;
        }
    }

}
