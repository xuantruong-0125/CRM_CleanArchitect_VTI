package org.example.crm_project.modules.customers_managerment.application.service;

import org.example.crm_project.modules.customers_managerment.application.dto.request.CreateAttachmentDTO;
import org.example.crm_project.modules.customers_managerment.application.dto.response.AttachmentResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AttachmentService {
    AttachmentResponseDTO createAttachment(CreateAttachmentDTO createDTO);
    AttachmentResponseDTO getAttachmentById(Long id);
    List<AttachmentResponseDTO> getAttachmentsByRelatedTo(String relatedToType, Long relatedToId);
    Page<AttachmentResponseDTO> getAttachmentsByRelatedToPaginated(String relatedToType, Long relatedToId, Pageable pageable);
    AttachmentResponseDTO updateAttachment(Long id, CreateAttachmentDTO createDTO);
    void deleteAttachment(Long id);
    void deleteAttachmentsByRelatedTo(String relatedToType, Long relatedToId);
    long countAttachments();
}
