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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

   @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<NoteResponse>> getNotes(
            @RequestParam("notableType") String notableType,
            @RequestParam("notableId") Long notableId) {
            
        return ResponseEntity.ok(noteService.getNotesByNotable(notableType, notableId));
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

    
}
