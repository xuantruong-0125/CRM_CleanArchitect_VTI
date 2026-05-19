package org.example.crm_project.modules.note_management.Domain.entity;

import org.example.crm_project.modules.activity_management.application.dto.request.CreateActivityRequest;

public class Note {
    private Long id;
    private String content;
    private String notableType;
    private Long notableId;
    private Boolean isPrivate;
    private Long createdBy;

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

    public Note() {
    }

    public Note(Long id, String content, String notableType, Long notableId, Boolean isPrivate, Long createdBy) {
        this.id = id;
        this.content = content;
        this.notableType = notableType;
        this.notableId = notableId;
        this.isPrivate = isPrivate;
        this.createdBy = createdBy;
    }

    public static Note toDomain(CreateActivityRequest request, Long activityId) {
        return new Note(
                null, // id (chưa có vì chưa lưu DB)
                request.getNoteContent(), // content
                "ACTIVITY", // notableType
                activityId, // notableId
                false, // isPrivate
                null // createdBy (có thể bổ sung sau)
        );
    }

    public void assignCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

}
