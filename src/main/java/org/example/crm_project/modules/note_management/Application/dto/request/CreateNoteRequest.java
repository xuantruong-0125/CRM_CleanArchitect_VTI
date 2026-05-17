package org.example.crm_project.modules.note_management.Application.dto.request;

public class CreateNoteRequest {
    private String content;
    private String notableType;
    private Long notableId;
    private Boolean isPrivate;

    // Constructor không tham số (Bắt buộc cho Jackson chuyển JSON)
    public CreateNoteRequest() {}

    // Các hàm Getter để lấy dữ liệu
    public String getContent() { return content; }
    public String getNotableType() { return notableType; }
    public Long getNotableId() { return notableId; }
    public Boolean getIsPrivate() { return isPrivate; }

    // // Các hàm Setter để Spring gán dữ liệu vào
    // public void setContent(String content) { this.content = content; }
    // public void setNotableType(String notableType) { this.notableType = notableType; }
    // public void setNotableId(Long notableId) { this.notableId = notableId; }
    // public void setIsPrivate(Boolean isPrivate) { this.isPrivate = isPrivate; }

}
