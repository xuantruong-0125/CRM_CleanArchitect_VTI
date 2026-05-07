package org.example.crm_project.modules.note_management.Application.service.contracts;
import java.util.List;

import org.example.crm_project.modules.note_management.Application.dto.request.CreateNoteRequest;
import org.example.crm_project.modules.note_management.Application.dto.response.NoteResponse;
import org.example.crm_project.modules.note_management.Domain.entity.Note;

public interface NoteService {
    // Hàm lưu Note (Dùng cho luồng tạo Activity/Task)
    void save(Note note);

    // Hàm lấy danh sách Note cho Activity
    List<NoteResponse> getNotesByActivityId(Long activityId);
    void deleteNote(Long id);


    NoteResponse createNote(CreateNoteRequest request);

}
