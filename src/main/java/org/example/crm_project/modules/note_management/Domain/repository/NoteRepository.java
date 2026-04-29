package org.example.crm_project.modules.note_management.Domain.repository;
import java.util.List;

import org.example.crm_project.modules.note_management.Domain.entity.Note;

public interface NoteRepository {
    
    // 1. Yêu cầu lưu một Ghi chú
    Note save(Note note);
    
    // 2. Yêu cầu xóa một Ghi chú
    void deleteById(Long id);
    
    // 3. Yêu cầu LẤY DANH SÁCH ghi chú của 1 đối tượng cụ thể (Vd: Activity số 10)
    List<Note> findByNotableTypeAndNotableId(String notableType, Long notableId);
}