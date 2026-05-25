package org.example.crm_project.modules.note_management.Application.mapper;

import org.example.crm_project.modules.activity_management.application.dto.request.CreateActivityRequest;
import org.example.crm_project.modules.note_management.Application.dto.request.CreateNoteRequest;
import org.example.crm_project.modules.note_management.Application.dto.response.NoteResponse;
import org.example.crm_project.modules.note_management.Domain.entity.Note;

public class NoteMapper {
    public static Note toDomain(CreateActivityRequest request, Long activityId) {
        return new Note(
                null, // id (chưa có vì đang đợi lưu DB)
                request.getNoteContent(), // content
                "ACTIVITY", // notableType
                activityId, // notableId
                false, // isPrivate
                null // createdBy (có thể bổ sung sau từ UserContext)
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
                // Domain Model hiện tại chưa có trường createdDate
            
                null);
    }

    public static Note toDomain(CreateNoteRequest request) {
        return new Note(
                null, // ID null vì là tạo mới
                request.getContent(),
                request.getNotableType(),
                request.getNotableId(),
                request.getIsPrivate() != null ? request.getIsPrivate() : false, // Mặc định là false nếu không truyền
                1L // Tạm hardcode người tạo là 1 (Sau này Duy nối Security vào sẽ lấy ID thật)
        );
    }

}
