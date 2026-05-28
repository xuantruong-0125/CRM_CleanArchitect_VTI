package org.example.crm_project.modules.note_management.Application.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.example.crm_project.modules.activity_management.domain.repository.ActivityUserProvider;
import org.example.crm_project.modules.auth.domain.entity.AuthUser;
import org.example.crm_project.modules.note_management.Application.dto.request.CreateNoteRequest;
import org.example.crm_project.modules.note_management.Application.dto.response.NoteResponse;
import org.example.crm_project.modules.note_management.Application.mapper.NoteMapper;
import org.example.crm_project.modules.note_management.Application.service.contracts.NoteService;
import org.example.crm_project.modules.note_management.Domain.entity.Note;


import org.example.crm_project.modules.note_management.Domain.repository.NoteRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    // private final NoteJpaRepository noteRepository;
    private final NoteRepository noteRepository;
    private final ActivityUserProvider userProvider;

    private AuthUser getCurrentAuthenticatedUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof AuthUser) {
            return (AuthUser) auth.getPrincipal();
        }
        throw new AccessDeniedException("Phiên đăng nhập không hợp lệ hoặc đã hết hạn!");
    }

   
    @Override
    public void deleteNote(Long id) {
        Note note = noteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy ghi chú"));

        AuthUser currentUser = getCurrentAuthenticatedUser();

        if (!currentUser.getId().equals(note.getCreatedBy())) {
            throw new AccessDeniedException("Bạn không có quyền xóa ghi chú của người khác!");
        }
        noteRepository.deleteById(id);
    }

    @Override
    public NoteResponse createNote(CreateNoteRequest request) {
        AuthUser currentUser = getCurrentAuthenticatedUser();

        // 1. Dùng Mapper chuyển Request thành Domain Model
        Note noteDomain = NoteMapper.toDomain(request);
        noteDomain.assignCreatedBy(currentUser.getId());

        // 2. Gọi Adapter lưu xuống Database (Hàm save của Adapter sẽ trả về Note có kèm
        // ID vừa tạo)
        Note savedNote = noteRepository.save(noteDomain);
        String creatorName = userProvider.getUserFullNameById(savedNote.getCreatedBy());

        // 3. Trả về Response cho Frontend 
        return NoteMapper.toResponse(savedNote, creatorName);
    }
    

    @Override
    public List<NoteResponse> getNotesByNotable(String notableType, Long notableId) {
        
        String safeType = notableType != null ? notableType.toUpperCase() : "";

        List<Note> notes = noteRepository.findByNotableTypeAndNotableId(safeType, notableId);
        
        return notes.stream()
                .map(note -> {
                    String creatorName = userProvider.getUserFullNameById(note.getCreatedBy());
                    return NoteMapper.toResponse(note, creatorName);
                }) 
                .collect(Collectors.toList());
    }

}
