package org.example.crm_project.modules.note_management.Application.mapper;

import org.example.crm_project.modules.activity_management.application.dto.request.CreateActivityRequest;
import org.example.crm_project.modules.note_management.Application.dto.request.CreateNoteRequest;
import org.example.crm_project.modules.note_management.Application.dto.response.NoteResponse;
import org.example.crm_project.modules.note_management.Domain.entity.Note;

public class NoteMapper {
    public static Note toDomain(CreateActivityRequest request, Long activityId) {
        return new Note(
                null, 
                request.getNoteContent(), 
                "ACTIVITY", 
                activityId, 
                false,
                null,
                null
        );
    }

    // Đổi tham số từ (NoteJpaEntity entity) thành (Note note)
    public static NoteResponse toResponse(Note note, String creatorName) {
        return new NoteResponse(
                note.getId(),
                note.getContent(),
                note.getNotableType(),
                note.getNotableId(),
                note.getIsPrivate(),
                note.getCreatedBy(),
                creatorName,
                note.getCreatedDate()
        );
    }

    public static Note toDomain(CreateNoteRequest request) {
        return new Note(
                null, 
                request.getContent(),
                request.getNotableType(),
                request.getNotableId(),
                request.getPrivateNote() != null ? request.getPrivateNote() : false,
                null,
                null 
        );
    }

}
