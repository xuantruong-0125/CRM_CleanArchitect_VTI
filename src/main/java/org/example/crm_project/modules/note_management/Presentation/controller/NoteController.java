package org.example.crm_project.modules.note_management.Presentation.controller;

import java.util.List;

import org.example.crm_project.modules.note_management.Application.dto.request.CreateNoteRequest;
import org.example.crm_project.modules.note_management.Application.dto.response.NoteResponse;
import org.example.crm_project.modules.note_management.Application.service.contracts.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ACTIVITY_VIEW')")
    @GetMapping("/activity/{id}")
    public ResponseEntity<List<NoteResponse>> getNotesByActivity(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getNotesByActivityId(id));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@RequestBody CreateNoteRequest request) {
        NoteResponse response = noteService.createNote(request);
        return ResponseEntity.ok(response);
    }

    // API lấy danh sách ghi chú của Task
    @PreAuthorize("hasAuthority('TASK_VIEW')")
    @GetMapping("/task/{id}")
    public ResponseEntity<List<NoteResponse>> getNotesByTask(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getNotesByTaskId(id));
    }
}
