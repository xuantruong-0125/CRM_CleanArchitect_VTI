package org.example.crm_project.modules.note_management.Application.dto.response;

import java.time.LocalDateTime;

public class NoteResponse {
    private Long id;
    private String content;
    private String notableType;
    private Long notableId;
    private Boolean isPrivate;
    private Long createdBy;
    private String creatorName; // Thêm tên người tạo để Frontend hiển thị cho đẹp
    private LocalDateTime createdDate;

    // Constructor không tham số
    public NoteResponse() {
    }

    // Constructor đầy đủ tham số
    public NoteResponse(Long id, String content, String notableType, Long notableId,
            Boolean isPrivate, Long createdBy, String creatorName, LocalDateTime createdDate) {
        this.id = id;
        this.content = content;
        this.notableType = notableType;
        this.notableId = notableId;
        this.isPrivate = isPrivate;
        this.createdBy = createdBy;
        this.creatorName = creatorName;
        this.createdDate = createdDate;
    }

    // Các hàm Getter (Chỉ cần Getter để Jackson chuyển thành JSON gửi về FE)
    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getNotableType() {
        return notableType;
    }

    public Long getNotableId() {
        return notableId;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
}
