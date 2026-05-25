package org.example.crm_project.modules.customers_managerment.application.mapper;

import org.example.crm_project.modules.customers_managerment.application.dto.request.CreateAttachmentDTO;
import org.example.crm_project.modules.customers_managerment.application.dto.response.AttachmentResponseDTO;
import org.example.crm_project.modules.customers_managerment.domain.entity.Attachment;
import org.springframework.stereotype.Component;

/**
 * Mapper: AttachmentMapper
 * DTO ↔ Domain entity conversion
 */
@Component
public class AttachmentMapper {

    public Attachment toEntity(CreateAttachmentDTO createDTO) {
        if (createDTO == null) return null;

        Attachment attachment = new Attachment();
        attachment.setFileName(createDTO.getFileName());
        attachment.setFileType(createDTO.getFileType());
        attachment.setFileSize(createDTO.getFileSize());
        attachment.setFilePath(createDTO.getFilePath());
        attachment.setRelatedToType(createDTO.getRelatedToType());
        attachment.setRelatedToId(createDTO.getRelatedToId());
        attachment.setUploadedBy(createDTO.getUploadedBy());

        return attachment;
    }

    public AttachmentResponseDTO toResponseDTO(Attachment attachment) {
        if (attachment == null) return null;

        AttachmentResponseDTO dto = new AttachmentResponseDTO();
        dto.setId(attachment.getId());
        dto.setFileName(attachment.getFileName());
        dto.setFileType(attachment.getFileType());
        dto.setFileSize(attachment.getFileSize());
        dto.setFilePath(attachment.getFilePath());
        dto.setRelatedToType(attachment.getRelatedToType());
        dto.setRelatedToId(attachment.getRelatedToId());
        dto.setUploadedBy(attachment.getUploadedBy());
        dto.setCreatedAt(attachment.getCreatedAt());

        return dto;
    }

    public void updateEntityFromDTO(Attachment attachment, CreateAttachmentDTO createDTO) {
        if (attachment == null || createDTO == null) return;

        attachment.setFileName(createDTO.getFileName());
        attachment.setFileType(createDTO.getFileType());
        attachment.setFileSize(createDTO.getFileSize());
        attachment.setFilePath(createDTO.getFilePath());
        attachment.setRelatedToType(createDTO.getRelatedToType());
        attachment.setRelatedToId(createDTO.getRelatedToId());
        attachment.setUploadedBy(createDTO.getUploadedBy());
    }
}
