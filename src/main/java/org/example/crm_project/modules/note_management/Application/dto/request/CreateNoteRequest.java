package org.example.crm_project.modules.note_management.Application.dto.request;

public class CreateNoteRequest {
    private String content;
    private String notableType;
    private Long notableId;
    private Boolean privateNote;

    public CreateNoteRequest() {}

    public String getContent() { return content; }
    public String getNotableType() { return notableType; }
    public Long getNotableId() { return notableId; }
    public Boolean getPrivateNote() { return privateNote; }

}
