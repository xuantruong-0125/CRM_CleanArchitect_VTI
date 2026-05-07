package org.example.crm_project.modules.note_management.Application.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.example.crm_project.modules.note_management.Application.dto.request.CreateNoteRequest;
import org.example.crm_project.modules.note_management.Application.dto.response.NoteResponse;
import org.example.crm_project.modules.note_management.Application.mapper.NoteMapper;
import org.example.crm_project.modules.note_management.Application.service.contracts.NoteService;
import org.example.crm_project.modules.note_management.Domain.entity.Note;

import org.example.crm_project.modules.note_management.infrastructure.persistence.entity.NoteJpaEntity;
import org.example.crm_project.modules.note_management.infrastructure.persistence.repository.NoteJpaRepository;
import org.example.crm_project.modules.note_management.Domain.repository.NoteRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    // private final NoteJpaRepository noteRepository;
    private final NoteRepository noteRepository;

    @Override
    public List<NoteResponse> getNotesByActivityId(Long activityId) {
        // 1. Gọi Repository lấy Entity
        List<Note> notes = noteRepository.findByNotableTypeAndNotableId("ACTIVITY", activityId);
        // 2. Map sang DTO để trả về
        return notes.stream()
                .map(note -> NoteMapper.toResponse(note, "Tên Người Tạo"))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Note note) {
        // Hàm này Duy đã viết logic lưu ở bước trước
    }

    @Override
    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }

    @Override
    public NoteResponse createNote(CreateNoteRequest request) {
        // 1. Dùng Mapper chuyển Request thành Domain Model
        Note noteDomain = NoteMapper.toDomain(request);

        // 2. Gọi Adapter lưu xuống Database (Hàm save của Adapter sẽ trả về Note có kèm
        // ID vừa tạo)
        Note savedNote = noteRepository.save(noteDomain);

        // 3. Trả về Response cho Frontend hiển thị ngay lập tức
        return new NoteResponse(
                savedNote.getId(),
                savedNote.getContent(),
                savedNote.getNotableType(),
                savedNote.getNotableId(),
                savedNote.getIsPrivate(),
                savedNote.getCreatedBy(),
                "Tên Người Tạo", // Chỗ này sau này ghép UserProvider vào để lấy tên thật
                java.time.LocalDateTime.now());
    }

}
