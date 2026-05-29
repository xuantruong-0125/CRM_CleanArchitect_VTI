package org.example.crm_project.modules.note_management.Application.service.contracts;

import java.util.List;

import org.example.crm_project.modules.note_management.Application.dto.request.CreateNoteRequest;
import org.example.crm_project.modules.note_management.Application.dto.response.NoteResponse;

public interface NoteService {

    void deleteNote(Long id);

    NoteResponse createNote(CreateNoteRequest request);

    List<NoteResponse> getNotesByNotable(String notableType, Long notableId);

}
