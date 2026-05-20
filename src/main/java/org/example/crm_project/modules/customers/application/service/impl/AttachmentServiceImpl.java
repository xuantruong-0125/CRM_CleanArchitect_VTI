package org.example.crm_project.modules.customers.application.service.impl;

import org.example.crm_project.modules.customers.application.dto.request.CreateAttachmentDTO;
import org.example.crm_project.modules.customers.application.dto.response.AttachmentResponseDTO;
import org.example.crm_project.modules.customers.application.mapper.AttachmentMapper;
import org.example.crm_project.modules.customers.application.service.AttachmentService;
import org.example.crm_project.modules.customers.domain.entity.Attachment;
import org.example.crm_project.modules.customers.domain.repository.AttachmentRepository;
import org.example.crm_project.modules.customers.domain.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation: AttachmentServiceImpl
 * Business logic for Attachment management
 */
@Service
@Transactional
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final AttachmentMapper attachmentMapper;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository, AttachmentMapper attachmentMapper) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentMapper = attachmentMapper;
    }

    @Override
    public AttachmentResponseDTO createAttachment(CreateAttachmentDTO createDTO) {
        Attachment attachment = attachmentMapper.toEntity(createDTO);
        Attachment saved = attachmentRepository.save(attachment);
        return attachmentMapper.toResponseDTO(saved);
    }

    @Override
    public AttachmentResponseDTO getAttachmentById(Long id) {
        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tệp đính kèm không tìm thấy: " + id));
        return attachmentMapper.toResponseDTO(attachment);
    }

    @Override
    public List<AttachmentResponseDTO> getAttachmentsByRelatedTo(String relatedToType, Long relatedToId) {
        List<Attachment> attachments = attachmentRepository.findByRelatedToTypeAndRelatedToId(relatedToType, relatedToId);
        return attachments.stream()
                .map(attachmentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<AttachmentResponseDTO> getAttachmentsByRelatedToPaginated(
            String relatedToType, Long relatedToId, Pageable pageable) {
        Page<Attachment> attachments = attachmentRepository.findByRelatedToTypeAndRelatedToId(relatedToType, relatedToId, pageable);
        return attachments.map(attachmentMapper::toResponseDTO);
    }

    @Override
    public AttachmentResponseDTO updateAttachment(Long id, CreateAttachmentDTO createDTO) {
        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tệp đính kèm không tìm thấy: " + id));
        attachmentMapper.updateEntityFromDTO(attachment, createDTO);
        Attachment updated = attachmentRepository.save(attachment);
        return attachmentMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteAttachment(Long id) {
        if (!attachmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Tệp đính kèm không tìm thấy: " + id);
        }
        attachmentRepository.delete(id);
    }

    @Override
    public void deleteAttachmentsByRelatedTo(String relatedToType, Long relatedToId) {
        attachmentRepository.deleteByRelatedToTypeAndRelatedToId(relatedToType, relatedToId);
    }

    @Override
    @Transactional(readOnly = true)
    public long countAttachments() {
        return attachmentRepository.count();
    }
}
