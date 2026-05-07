package org.example.crm_project.modules.note_management.Presentation.controller;

import java.util.List;

import org.example.crm_project.modules.note_management.Application.dto.request.CreateNoteRequest;
import org.example.crm_project.modules.note_management.Application.dto.response.NoteResponse;
import org.example.crm_project.modules.note_management.Application.service.contracts.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    // API lấy danh sách ghi chú của Activity
    @GetMapping("/activity/{id}")
    public ResponseEntity<List<NoteResponse>> getNotesByActivity(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getNotesByActivityId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);

        // Trả về HTTP Status 204 (No Content) - Báo hiệu xóa thành công
        return ResponseEntity.noContent().build();
    }
    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@RequestBody CreateNoteRequest request) {
        NoteResponse response = noteService.createNote(request);
        return ResponseEntity.ok(response);
    }


}
