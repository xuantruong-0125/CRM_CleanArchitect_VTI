package org.example.crm_project.modules.note_management.Application.dto.response;

import java.time.LocalDateTime;

public class NoteResponse {
    private Long id;
    private String content;
    private String notableType;
    private Long notableId;
    private Boolean privateNote;
    private Long createdBy;
    private String creatorName; 
    private LocalDateTime createdDate;

    public NoteResponse() {
    }

    public NoteResponse(Long id, String content, String notableType, Long notableId,
            Boolean privateNote, Long createdBy, String creatorName, LocalDateTime createdDate) {
        this.id = id;
        this.content = content;
        this.notableType = notableType;
        this.notableId = notableId;
        this.privateNote = privateNote;
        this.createdBy = createdBy;
        this.creatorName = creatorName;
        this.createdDate = createdDate;
    }


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

    public Boolean getPrivateNote() {
        return privateNote;
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
