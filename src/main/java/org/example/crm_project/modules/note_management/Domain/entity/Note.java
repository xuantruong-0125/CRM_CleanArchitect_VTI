package org.example.crm_project.modules.note_management.Domain.entity;

import java.time.LocalDateTime;

import org.example.crm_project.modules.activity_management.application.dto.request.CreateActivityRequest;

public class Note {
    private Long id;
    private String content;
    private String notableType;
    private Long notableId;
    private Boolean isPrivate;
    private Long createdBy;
    private LocalDateTime createdDate;

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
    public LocalDateTime getCreatedDate() { return createdDate; }

    public Note() {
    }

    public Note(Long id, String content, String notableType, Long notableId, Boolean isPrivate, Long createdBy,LocalDateTime createdDate) {
        this.id = id;
        this.content = content;
        this.notableType = notableType;
        this.notableId = notableId;
        this.isPrivate = isPrivate;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

   
    public void assignCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

}
